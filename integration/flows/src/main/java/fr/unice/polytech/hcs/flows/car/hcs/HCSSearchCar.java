package fr.unice.polytech.hcs.flows.car.hcs;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class HCSSearchCar extends RouteBuilder {


    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);


    @Override
    public void configure() throws Exception {

        /*
        * HCS search car route
        * */


        from(HCS_SEARCH_CAR_MQ)

                .routeId("car-rental-hcs")
                .routeDescription("Loads a route to carWS")
                .split(body())
                .parallelProcessing().executorService(WORKERS)
                .log("Transforming a CSV line into a Person")
                // put headers properties
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                // Process to the translation. We take just what we need.
                // Fields are the same as the api.md from the carWS
                .process((Exchange exchange) -> {
                    Map<String, Object> v = (Map<String, Object>) exchange.getIn().getBody();
                    Car car = new Car();
                    // add the next step later
                    car.setCity((String) v.get("city"));
                    // set the dateFrom from the initial request.
                    car.setDateFrom((String) v.get("dateFrom"));
                    // set the dateTo from the initial request.
                    car.setDateTo((String) v.get("dateTo"));
                    // put changes.
                    exchange.getIn().setBody(car);
                })
                // We wait the answer of the endpoint.
                .multicast(new GroupedExchangeAggregationStrategy())
                .parallelProcessing()
                .inOut(HCS_SEARCH_CAR_EP, G2_SEARCH_CAR_EP)
                .choice()
                .when(body())
                .marshal().json(JsonLibrary.Jackson);
    }
}
