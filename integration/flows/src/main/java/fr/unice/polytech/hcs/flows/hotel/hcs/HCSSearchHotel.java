package fr.unice.polytech.hcs.flows.hotel.hcs;

import fr.unice.polytech.hcs.flows.hotel.HotelRequest;
import fr.unice.polytech.hcs.flows.hotel.SearchHotel;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_HOTEL_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_HOTEL_MQ;

public class HCSSearchHotel extends RouteBuilder {


    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);


    @Override
    public void configure() throws Exception {

        from(HCS_SEARCH_HOTEL_MQ)
                .routeId("hotel-search")
                .routeDescription("Loads a route to hotelWS")
                .split(body())
                .parallelProcessing().executorService(WORKERS)
                .log("Transforming a CSV line into a Hotel Request")
                // put headers properties
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                // Process to the translation. We take just what we need.
                // Fields are the same as the api.md from the carWS
                .process((Exchange exchange) -> {
                    HCSHotelRequest req = new HCSHotelRequest((HotelRequest) exchange.getIn().getBody());
                    exchange.getIn().setBody(req);
                })
                // We wait the answer of the endpoint.
                .inOut(HCS_SEARCH_HOTEL_EP)
                .marshal().json(JsonLibrary.Jackson);
    }
}
