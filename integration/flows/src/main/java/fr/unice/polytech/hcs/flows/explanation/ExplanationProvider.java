package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.bson.types.ObjectId;

import java.util.Collections;
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
                .inOut(GET_TRAVEL)
                .removeHeader("CamelHttp*")
                .log("[ " + EXPLANATION_PROVIDER + "]" + "UTILISER LE OOOOOOOUUUUUUUTTTTTTT qui est un MongoDBObject (une map++)")

                .process(exchange -> {

                    // Cast to a MAP because the POJO is quiet complexe.
                    Map body = exchange.getIn().getBody(Map.class);
                    body.put("explanation", exchange.getIn().getHeader("explanation") );
                    
                    exchange.getIn().setBody(body);
                    System.out.println("send : " + body);
                })
        .log("[ " + EXPLANATION_PROVIDER + "]" + "Object updated  !")

        .to(SAVE_TRAVEL_DATABASE_EP)

        ;

//

        restConfiguration()
                .component("servlet")
        ;

        rest("/explanation")
                .post("/answer")
                .to(EXPLANATION_ANSWER)
        ;
        from(EXPLANATION_ANSWER)
                .routeId("explanation-answer")
                .routeDescription("answer to an explanation.")
                .log("[" + EXPLANATION_ANSWER + "] Received answer")

                .log("[" + EXPLANATION_ANSWER+ "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")

                .log("[" + EXPLANATION_ANSWER + "] Prepare request parameters for DB search travel")
                .unmarshal().json(JsonLibrary.Jackson, ExplanationAnswer.class)
                .process(e -> {
                    ExplanationAnswer explanationAnswer = e.getIn().getBody(ExplanationAnswer.class);
                    e.getIn().setBody(
                            Collections.singletonMap("_id", new ObjectId(explanationAnswer.id)));
                    e.getIn().setHeader("code", Integer.toString(explanationAnswer.code));
                })

                .log("[" + EXPLANATION_ANSWER + "]" + "request to the database send.")
                .inOut(GET_TRAVEL)
                .log("[" + EXPLANATION_ANSWER + "]" + "Value retrieve from Db !")

                .process(exchange -> {
                    Map body = exchange.getIn().getBody(Map.class);
                    exchange.getIn().setHeader("explanation", body.get("explanation"));

                })
                .log("[" + EXPLANATION_ANSWER + "]" + "Make a choice.")

                .choice()
                .when(simple("${header.code} == 1"))
                .process(exchange -> {
                    Map body = exchange.getIn().getBody(Map.class);
                    Travel travel = new Travel();
                    travel.documents = (List<Expense>) body.get("documents");
                    travel.status = (String) body.get("status");
                    travel.travelId = (Integer) body.get("travelId");


                    exchange.getIn().setBody(travel);
                })
                .log("ACCEPT : refundement : ${header.explanation} is correct and accepted, well done :D")
                .to(ACCEPT_REFUND)
                .when(simple("${header.code} == 0"))
                .log("ERROR refundement : ${header.explanation} is not correct for your manager ;-) " );




    }
}
