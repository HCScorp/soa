package fr.unice.polytech.hcs.flows.flight;

import fr.unice.polytech.hcs.flows.splitator.CheapestAggregator;
import fr.unice.polytech.hcs.flows.splitator.SplittatorRoute;
import org.apache.camel.Endpoint;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchFlight extends SplittatorRoute<FlightSearchRequest, FlightSearchResponse, Flight> {

    public SearchFlight() {
        super("/flight",
                FlightSearchRequest.class,
                FlightSearchResponse.class,
                Flight.class,
                new CheapestAggregator<>(FlightSearchResponse.class, Flight.class),
                SEARCH_FLIGHT_INPUT,
                SEARCH_FLIGHT_MQ,
                Arrays.asList(HCS_SEARCH_FLIGHT_MQ, G1_SEARCH_FLIGHT_MQ),
                2,
                2000,
                "search-flight-input",
                "Perform a generic flight search request");
    }
}
