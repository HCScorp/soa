package fr.unice.polytech.hcs.flows.explanation;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bson.types.ObjectId;

import java.util.Collections;

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

        from(EXPLANATION_PROVIDER)
                .routeId("explanation-provider")
                .log("[" + EXPLANATION_PROVIDER + "] Received explanation")

                .log("[" + EXPLANATION_PROVIDER + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")

                .log("[" + EXPLANATION_PROVIDER + "] Prepare request parameters for DB search route")
                .unmarshal().json(JsonLibrary.Jackson, Explanation.class)
                .process(e -> e.getIn().setBody(
                        Collections.singletonMap("_id", new ObjectId(e.getIn().getBody(Explanation.class).id))))

                .log("[" + EXPLANATION_PROVIDER + "] Send to DB search route")
                .inOut(GET_TRAVEL)

                .log("[ " + EXPLANATION_PROVIDER + "]" + "UTILISER LE OOOOOOOUUUUUUUTTTTTTT qui est un MongoDBObject (une map++)")
        ;

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
