package fr.unice.polytech.hcs.flows.error;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.ERROR_MQ;

public class ErrorHandler extends RouteBuilder {

    private static final int REDELIVERIES = 4;
    private static final long REDELIVERY_DELAY = 500;

    @Override
    public void configure() throws Exception {
        errorHandler(
                deadLetterChannel(ERROR_MQ)
                        .useOriginalMessage()
                        .maximumRedeliveries(REDELIVERIES)
                        .redeliveryDelay(REDELIVERY_DELAY)
        );
    }
}
