package fr.unice.polytech.hcs.flows.flight;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchFlight extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(2);

    @Override
    public void configure() throws Exception {

        rest("/flight")
                .post("/search").to(SEARCH_FLIGHT_INPUT);

        from(SEARCH_FLIGHT_INPUT)
                .routeId("search-flight-input")
                .routeDescription("Create generic flight search request")

                .log("Load flight request")
                .unmarshal(new JsonDataFormat())

                .log("Transform a flight request into a flight request object")
                .process(jsonToFlightSearchRequest)
                .to(SEARCH_FLIGHT_MQ)
        ;

        from(SEARCH_FLIGHT_MQ)
                // Forwards to service and wait for responses
                .multicast(new GroupedExchangeAggregationStrategy())
                    .parallelProcessing()
                    .executorService(WORKERS)
                    .timeout(2000) // 2 seconds
                    .inOut(HCS_SEARCH_FLIGHT_MQ, G1_SEARCH_FLIGHT_MQ)
                .choice()
                    .when(simple("${body.form.price1} >= ${body.form.price2}")) // TODO
                        .process("") // TODO
                    .otherwise()
                        .process("") // TODO
                .end();
    }

    private static Processor jsonToFlightSearchRequest = (Exchange exchange) -> {
        Map<String, Object> data = (Map<String, Object>) exchange.getIn().getBody();
        FlightSearchRequest fsr = new FlightSearchRequest();

        fsr.origin = (String) data.get("origin");
        fsr.destination = (String) data.get("destination");
        fsr.date = (String) data.get("date");
        fsr.timeFrom = (String) data.get("timeFrom");
        fsr.timeTo = (String) data.get("timeTo");
        fsr.journeyType = (String) data.get("journeyType");
        fsr.maxTravelTime = (int) data.get("maxTravelTime");
        fsr.category = (String) data.get("category");
        fsr.airline = (String) data.get("airline");
        fsr.order = (String) data.get("order");

        exchange.getIn().setBody(fsr);
    };
}
