package fr.unice.polytech.hcs.flows.explanation;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ExplanationProvider extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from(EXPLANATION_PROVIDER)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .to(EXPLANATION_CHECKER);


        from(EXPLANATION_ANSWER)
                .choice()
                .when(simple("${header.code} == 1"))
                .process(exchange -> {
                    ExplanationAnswer explanationAnswer = exchange.getIn().getBody(ExplanationAnswer.class);
                    exchange.getIn().setHeader("code", explanationAnswer.code);
                    exchange.getIn().setBody(explanationAnswer.travel);
                })
                .to(REFUND_ACCEPT)
                .when(simple("${header.code} == 0"))
                .to(EXPLANATION_REFUSED);


        from(EXPLANATION_REFUSED)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .to(EXPLANATION_REFUSED_EP);



        from(REFUND_ACCEPT)
                .log("refund accepted !");
    }
}
