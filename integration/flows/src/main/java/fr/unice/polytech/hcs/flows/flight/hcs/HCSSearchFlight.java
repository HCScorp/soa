package fr.unice.polytech.hcs.flows.flight.hcs;

import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_MQ;

public class HCSSearchFlight extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from(HCS_SEARCH_FLIGHT_MQ)
                .routeId("hcs-search-flight-ws")
                .routeDescription("Send the flight search request to the flight WS")

                .log("Create request for HCS flight WS ")
                .process((Exchange exc) -> {
                    HCSFlightSearchRequest req = new HCSFlightSearchRequest((FlightSearchRequest) exc.getIn().getBody());
                    exc.getIn().setBody(req);
                })

                .log("${body.origin} -> ${body.destination} Marshalling request")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))

                .log("Marshalling into a JSON body")
                .marshal().json(JsonLibrary.Jackson)
                .to(Endpoints.HCS_SEARCH_FLIGHT_EP)
        ;
    }
}
