package fr.unice.polytech.hcs.flows.explanation;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ExplanationProvider extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
        ;

        rest("/explanation")
                .post("/send")
                .type(Explanation.class)
                .to(SEARCH_TRAVEL)
        ;
        // we suppose that the manager contact directly the user to send him a explanation for his voyage.
        // But into a Db
        from(EXPLANATION_PROVIDER)
                .process(exchange -> {
                    Explanation explanation = exchange.getIn().getBody(Explanation.class);
                    HashMap<String, Integer> h = new HashMap<>();
                    h.put("travelId", explanation.id);
                    exchange.getIn().setBody(h);
                })
                .inOut(GET_TRAVEL)

        .log("We send a mail to the manager");





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
