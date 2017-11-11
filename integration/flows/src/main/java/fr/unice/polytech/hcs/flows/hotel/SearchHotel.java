package fr.unice.polytech.hcs.flows.hotel;

import fr.unice.polytech.hcs.flows.splitator.CheapestAggregator;
import fr.unice.polytech.hcs.flows.splitator.SplittatorRoute;

import java.util.Arrays;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchHotel extends SplittatorRoute<HotelSearchRequest, HotelSearchResponse, Hotel> {

    public SearchHotel() {
        super("/hotel",
                HotelSearchRequest.class,
                Hotel.class,
                new CheapestAggregator<>(HotelSearchResponse.class, Hotel.class),
                SEARCH_HOTEL_INPUT,
                SEARCH_HOTEL_MQ,
                Arrays.asList(HCS_SEARCH_HOTEL_MQ, G7_SEARCH_HOTEL_MQ),
                2,
                30000,
                "search-hotel-input",
                "Execute a generic hotel search request");
    }
}
