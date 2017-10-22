package fr.unice.polytech.hcs.flows.hotel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_HOTEL_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_HOTEL_MQ;

public class SearchHotel extends RouteBuilder {


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
                    Map<String, Object> v = (Map<String, Object>) exchange.getIn().getBody();
                    HotelRequest hotelRequest = new HotelRequest();

                    hotelRequest.setCity((String) v.get("city"));
                    hotelRequest.setDateFrom((String) v.get("dateFrom"));
                    hotelRequest.setDateTo((String) v.get("dateTo"));
                    hotelRequest.setOrder((String) v.get("order"));

                    exchange.getIn().setBody(hotelRequest);
                })
                // We wait the answer of the endpoint.
                .inOut(HCS_SEARCH_HOTEL_EP)
                .marshal().json(JsonLibrary.Jackson);
    }
}
