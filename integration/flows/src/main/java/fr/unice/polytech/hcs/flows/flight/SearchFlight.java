package fr.unice.polytech.hcs.flows.flight;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

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
                .log("Parse flight request from JSON to FSReq")
                .unmarshal().json(JsonLibrary.Jackson, FlightSearchRequest.class)

                .log("Send FSReq to flight queue")
                .to(SEARCH_FLIGHT_MQ)
        ;

        from(SEARCH_FLIGHT_MQ)
                // Forwards to services and wait for responses
                .multicast(new GroupedExchangeAggregationStrategy())
                    .executorService(WORKERS)
                    .parallelProcessing()
                    .parallelAggregate()
                    .split(body(), combineBody)
                    .timeout(2000) // 2 seconds
                    .inOut(HCS_SEARCH_FLIGHT_MQ, G1_SEARCH_FLIGHT_MQ)
                .marshal().json(JsonLibrary.Jackson)
//                .choice()
//                    .when(simple("${body.form.1.price} >= ${body.form.2.price}")) // TODO
//                        .process("") // TODO
//                    .otherwise()
//                        .process("") // TODO
//                .end()
        ;
    }

    private static AggregationStrategy combineBody = (e1, e2) -> {
        if (e1 == null) {
            return e2;
        }

        FlightSearchResponse fsr1 = e1.getIn().getBody(FlightSearchResponse.class);
        FlightSearchResponse fsr2 = e2.getIn().getBody(FlightSearchResponse.class);

        Flight cheapest = null;
        for(Flight f : fsr1.result) {
            if(cheapest == null || f.price < cheapest.price) {
                cheapest = f;
            }
        }

        for(Flight f : fsr2.result) {
            if(cheapest == null || f.price < cheapest.price) {
                cheapest = f;
            }
        }

        e2.getIn().setBody(cheapest);

        return e2;
    };
}
