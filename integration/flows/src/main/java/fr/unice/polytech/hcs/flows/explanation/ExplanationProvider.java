package fr.unice.polytech.hcs.flows.explanation;

import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ExplanationProvider extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
        ;

        rest("/explanation")
                .post()
                .to(EXPLANATION_PROVIDER)
        ;


        // Explanation for why did you use so much money dude.
        from(EXPLANATION_PROVIDER)
                .routeId("explanation-provider")
                .log("[" + EXPLANATION_PROVIDER + "] Received explanation: ${body}")

                .log("[" + EXPLANATION_PROVIDER + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")

                .log("[" + EXPLANATION_PROVIDER + "] Prepare request parameters for DB search route")

                //User send a Explanation POJO to excuse .
                .unmarshal().json(JsonLibrary.Jackson, Explanation.class)

                .process(e -> {

                    //We we'll use directly a explanation object.
                    Explanation explanation = e.getIn().getBody(Explanation.class);

                    // We need the travel ID from mongo DB to catch the travel that the user speak about.
                    e.getIn().setBody(
                            Collections.singletonMap("_id", new ObjectId(explanation.id)));

                    // The explanation that we'll use later to accept or not.
                    e.getIn().setHeader("explanation", explanation.explanation);

                })

                .log("[" + EXPLANATION_PROVIDER + "] Send to DB search route")

                .inOut(GET_TRAVEL_DB_OBJECT)
                .removeHeader("CamelHttp*")
                .log("[ " + EXPLANATION_PROVIDER + "]" + " We receive the data from the database.")

                .process(exchange -> {

                    // Cast to a MAP because the POJO is quiet complexe.
                    Map body = exchange.getIn().getBody(Map.class);
                    body.put("explanation", exchange.getIn().getHeader("explanation"));

                    exchange.getIn().setBody(body);
                    System.out.println("send : " + body);
                })
                .log("[ " + EXPLANATION_PROVIDER + "]" + "Object updated  !")

                .to(SAVE_TRAVEL_DATABASE_EP)

        ;

        // Route for answering to an explanation request. actor : manager. receiver : user.

        restConfiguration()
                .component("servlet")
        ;

        rest("/explanation")
                .post("/answer")
                .to(EXPLANATION_ANSWER)

        ;
        from(EXPLANATION_ANSWER)
                // Initial stuff
                .routeId("explanation-answer")
                .routeDescription("answer to an explanation.")
                .log("[" + EXPLANATION_ANSWER + "] Received answer")

                .log("[" + EXPLANATION_ANSWER + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")


                .log("[" + EXPLANATION_ANSWER + "] Prepare request parameters for DB search travel")
                
                //Unmarshal to a ExplanationRequest pojo : ID travel + code answer.
                .unmarshal().json(JsonLibrary.Jackson, ExplanationAnswer.class)

                // Process the content of a ExplanationAnswer in order to : catch the travel code
                // Save the answered code.
                .process(e -> {
                    ExplanationAnswer explanationAnswer = e.getIn().getBody(ExplanationAnswer.class);
                    e.getIn().setBody(
                            Collections.singletonMap("_id", new ObjectId(explanationAnswer.id)));
                    e.getIn().setHeader("code", Integer.toString(explanationAnswer.code));
                })

                .log("[" + EXPLANATION_ANSWER + "]" + "request to the database send.")
                //take the result from the DB request.
                .inOut(GET_TRAVEL_DB_OBJECT)

                .log("[" + EXPLANATION_ANSWER + "]" + "Value retrieve from Db !")
                // Get the content of the travel that the manager have accepted or rejected.
                .process(exchange -> {
                    Map body = exchange.getIn().getBody(Map.class);
                    exchange.getIn().setHeader("explanation", body.get("explanation"));

                })
                .log("[" + EXPLANATION_ANSWER + "]" + "Make a choice.")

                // Format the body to the travel we caught before.
                .process(exchange -> {
                    // the proper body of teh request.
                    Map body = exchange.getIn().getBody(Map.class);
                    Travel travel = new Travel();

                    //fill the pojo
                    travel.documents = (List<Expense>) body.get("documents");
                    travel.status = (String) body.get("status");
                    travel.travelId = (Integer) body.get("travelId");

                    // change the contente of the body.
                    exchange.getIn().setBody(travel);
                })
                // Make a choice between it's accepted or rejected.
                .choice()

                // code == 1 ==> accepted.
                .when(simple("${header.code} == 1"))
                    .log("ACCEPT : refundement : ${header.explanation} is correct and accepted, well done :D")
                    .inOut(EXPLANATION_ACCEPTED)

                // Otherwise ...
                .otherwise()
                    .log("ERROR refundement : ${header.explanation} is not correct for your manager ;-) ")
                    .inOut(EXPLANATION_REFUSED)

                .end()
                // Format the ended message for user.
                .marshal().json(JsonLibrary.Jackson);


        // Route to notify to user that the explanation he make have been Rejected by the manager.
        // Update de value in the DB + notification to the manager that the proper work has been done.

        from(EXPLANATION_REFUSED)
                .routeId("explanation-refused")
                .routeDescription("reject explanation")

                .log("[" + EXPLANATION_REFUSED + "] Reject explanation")
                .process(e -> {
                    Travel travel = e.getIn().getBody(Travel.class);
                    travel.status = "Rejected";
                    e.getIn().setBody(travel);
                })

                .log("[" + EXPLANATION_REFUSED + "] ")
                .to(UPDATE_TRAVEL)
                .process(e -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "Reject");
                    response.put("message", "Refund Reject.");

                    e.getIn().setBody(response);
                })
        ;

        // Route to notify to user that the explanation he make have been Accepted by the manager.
        // Update de value in the DB + notification to the manager that the proper work has been done.

        from(EXPLANATION_ACCEPTED)
                .routeId("explanation-accepted")
                .routeDescription("Accept a explanation")

                .log("[" + EXPLANATION_ACCEPTED + "] Accept explanation")
                .process(e -> {
                    Travel travel = e.getIn().getBody(Travel.class);
                    travel.status = "Done";
                    e.getIn().setBody(travel);
                })

                .log("[" + EXPLANATION_ACCEPTED + "] : update travel with new status")
                .to(UPDATE_TRAVEL)

                .process(e -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "Done");
                    response.put("message", "Refund accepted.");

                    e.getIn().setBody(response);
                })
        ;
    }
}
