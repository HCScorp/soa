package fr.unice.polytech.hcs.flows.flight.hcs;

import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_MQ;

public class HCSSearchFlight extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(HCS_SEARCH_FLIGHT_MQ)
                .routeId("hcs-search-flight-ws")
                .routeDescription("Send the flight search request to the flight WS")

                .log("Create request for HCS flight WS")
                .process(e -> {
                    HCSFlightSearchRequest req = new HCSFlightSearchRequest((FlightSearchRequest) e.getIn().getBody());
                    e.getIn().setBody(req);
                })

                .log("Setting up request header")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))

                .log("Marshalling body into JSON")
                .marshal().json(JsonLibrary.Jackson)

                .log("Sending to HCS search flight endpoint")
                .inOut(HCS_SEARCH_FLIGHT_EP)

                .log("Unmarshal JSON response to Flight Search Response")
                .unmarshal().json(JsonLibrary.Jackson, FlightSearchResponse.class)
                .log("Received ${body.length} flight results")
        ;
    }
}
