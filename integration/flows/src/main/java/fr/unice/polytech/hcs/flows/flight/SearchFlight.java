package fr.unice.polytech.hcs.flows.flight;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchFlight extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

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
                .inOut(HCS_SEARCH_FLIGHT_MQ, OTHER_SEARCH_FLIGHT_MQ)
        ;

    }

    private static Processor jsonToFlightSearchRequest = (Exchange exchange) -> {
        Map<String, Object> data = (Map<String, Object>) exchange.getIn().getBody();
        FlightSearchRequest fsr = new FlightSearchRequest();

        fsr.setOrigin((String) data.get("origin"));
        fsr.setDestination((String) data.get("destination"));
        fsr.setDate((String) data.get("date"));
        fsr.setTimeFrom((String) data.get("timeFrom"));
        fsr.setTimeTo((String) data.get("timeTo"));
        fsr.setJourneyType((String) data.get("journeyType"));
        fsr.setMaxTravelTime((int) data.get("maxTravelTime"));
        fsr.setCategory((String) data.get("category"));
        fsr.setAirline((String) data.get("airline"));
        fsr.setOrder((String) data.get("order"));

        exchange.getIn().setBody(fsr);
    };
}
