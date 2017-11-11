package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ExplanationProvider extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
        ;

        rest("/send")
                .post("/explain")
                .type(Explanation.class)
                .to(EXPLANATION_PROVIDER)
        ;
        // we suppose that the manager contact directly the user to send him a explanation for his voyage.
        // But into a Db

        from(EXPLANATION_PROVIDER)
                .routeId("explanation-provider")

                .log("Explanation provider ")
                .unmarshal().json(JsonLibrary.Jackson)
                .process(exchange -> {


                    System.out.println(exchange.getIn().getBody());
                    Map map = exchange.getIn().getBody(Map.class);
                    System.out.println("map = " + map);
                    HashMap<String, Object> h = new HashMap<>();
                    h.put("travelId", map.get("id"));

                    exchange.getIn().setBody(h);
                    exchange.getIn().setHeader("explain", map.get("explanation"));

                    System.out.println("I put : " + h);

                })
                .to(SEARCH_TRAVEL_DATABASE_EP)
                .log(" [ " + SEARCH_TRAVEL + "]" + "send the travel given")
                .process(e -> {
                    System.out.println("I receive from Db : " + e.getIn().getBody());
                    System.out.println("type : " + e.getIn().getBody().getClass());

                });
//        ;

//
//        from(EXPLANATION_ANSWER)
//                .choice()
//                .when(simple("${header.code} == 1"))
//                .process(exchange -> {
//                    ExplanationAnswer explanationAnswer = exchange.getIn().getBody(ExplanationAnswer.class);
//                    exchange.getIn().setHeader("code", explanationAnswer.code);
//                     exchange.getIn().setBody(explanationAnswer.code);
//                })
//                .to(REFUND_ACCEPT)
//                .when(simple("${header.code} == 0"))
//                .to(EXPLANATION_REFUSED);
//
//
//        from(EXPLANATION_REFUSED)
//                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
//                .setHeader("Content-Type", constant("application/json"))
//                .setHeader("Accept", constant("application/json"))
//                .to(EXPLANATION_REFUSED_EP);
//
//
//
//        from(REFUND_ACCEPT)
//                .log("refund accepted !");


    }
}
