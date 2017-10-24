package fr.unice.polytech.hcs.flows.hotel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchHotel extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(2);

    @Override
    public void configure() throws Exception {

        rest("/hotel")
                .post("/search").to(SEARCH_HOTEL_INPUT);

        from(SEARCH_HOTEL_INPUT)
                .routeId("search-hotel-input")
                .routeDescription("Create generic hotel search request")

                .log("Load hotel request")
                .unmarshal(new JsonDataFormat())

                .log("Transform a hotel request into a hotel request object")
                .process(jsonToHotelRequest)
                .to(SEARCH_FLIGHT_MQ)
        ;

        from(SEARCH_HOTEL_MQ)
                // Forwards to service and wait for responses
                .multicast(new GroupedExchangeAggregationStrategy())
                .parallelProcessing()
                .executorService(WORKERS)
                .timeout(2000) // 2 seconds
                .inOut(HCS_SEARCH_HOTEL_MQ, G7_SEARCH_HOTEL_MQ)
                .choice()
                .when(simple("${body.form.price1} >= ${body.form.price2}")) // TODO
                .process("") // TODO
                .otherwise()
                .process("") // TODO
                .end();
    }

    private static Processor jsonToHotelRequest = (Exchange exchange) -> {
        Map<String, Object> data = (Map<String, Object>) exchange.getIn().getBody();
        HotelSearchRequest hr = new HotelSearchRequest();

        hr.city = (String) data.get("city");
        hr.dateFrom = (String) data.get("dateFrom");
        hr.dateTo = (String) data.get("dateTo");
        hr.order = (String) data.get("order");

        exchange.getIn().setBody(hr);
    };
}
