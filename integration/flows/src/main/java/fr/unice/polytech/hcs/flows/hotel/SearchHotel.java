package fr.unice.polytech.hcs.flows.hotel;

import fr.unice.polytech.hcs.flows.splitator.CheapestAggregator;
import fr.unice.polytech.hcs.flows.splitator.SplittatorRoute;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchHotel extends SplittatorRoute<HotelSearchRequest, HotelSearchResponse, Hotel> {

    protected SearchHotel() {
        super("/hotel",
                HotelSearchRequest.class,
                HotelSearchResponse.class,
                Hotel.class,
                new CheapestAggregator<>(HotelSearchResponse.class, Hotel.class),
                SEARCH_HOTEL_INPUT,
                SEARCH_HOTEL_MQ,
                Arrays.asList(HCS_SEARCH_HOTEL_MQ, G7_SEARCH_HOTEL_MQ),
                2,
                10000,
                "search-hotel-input",
                "Execute a generic hotel search request");
    }
}
