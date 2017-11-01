package fr.unice.polytech.hcs.flows.car;

import fr.unice.polytech.hcs.flows.splitator.CheapestAggregator;
import fr.unice.polytech.hcs.flows.splitator.SplittatorRoute;

import java.util.Arrays;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchCar extends SplittatorRoute<CarSearchRequest, CarSearchResponse, Car> {

    public SearchCar() {
        super("/car",
                CarSearchRequest.class,
                CarSearchResponse.class,
                Car.class,
                new CheapestAggregator<>(CarSearchResponse.class, Car.class),
                SEARCH_CAR_INPUT,
                SEARCH_CAR_MQ,
                Arrays.asList(HCS_SEARCH_CAR_MQ, G2_SEARCH_CAR_MQ),
                2,
                10000,
                "search-car-input",
                "Execute a generic car search request");
    }
}
