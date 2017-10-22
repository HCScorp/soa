package fr.unice.polytech.hcs.flows.car;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_CAR_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_CAR_MQ;

public class SearchCar extends RouteBuilder {


    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);


    @Override
    public void configure() throws Exception {

        from(HCS_SEARCH_CAR_MQ)
                .routeId("car-rental")
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
                .inOut(HCS_SEARCH_CAR_EP)
                .marshal().json(JsonLibrary.Jackson);
    }
}
