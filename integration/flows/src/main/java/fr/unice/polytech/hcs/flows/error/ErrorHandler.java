package fr.unice.polytech.hcs.flows.error;

import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.ERROR_MQ;

public class ErrorHandler extends RouteBuilder {

    private static final int REDELIVERIES = 100;
    private static final long REDELIVERY_DELAY = 500;

    @Override
    public void configure() throws Exception {
        errorHandler(
                deadLetterChannel(ERROR_MQ)
                        .useOriginalMessage()
                        .maximumRedeliveries(REDELIVERIES)
                        .redeliveryDelay(REDELIVERY_DELAY)
        );

        from(Endpoints.BAD_REQUEST)
                .routeId("bad-request")
                .routeDescription("Bad request")

                .log("Bad request")
                .process(e -> e.getIn().setBody(null))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
        ;
    }
}
