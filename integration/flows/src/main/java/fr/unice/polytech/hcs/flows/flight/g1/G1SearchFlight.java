package fr.unice.polytech.hcs.flows.flight.g1;


import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G1_SEARCH_FLIGHT_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G1_SEARCH_FLIGHT_MQ;

public class G1SearchFlight extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(G1_SEARCH_FLIGHT_MQ)
                .routeId("g1-search-flight-ws")
                .routeDescription("Send the flight search request to the flight WS")

                .log("Create request for G1 flight WS ")
                .process((Exchange exc) -> {
                    G1FlightSearchRequest req = new G1FlightSearchRequest((FlightSearchRequest) exc.getIn().getBody());
                    exc.getIn().setBody(req);
                })

                .log("${body.origin} -> ${body.destination} Marshalling request")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))

                .log("Marshalling into a JSON body")
                .marshal().json(JsonLibrary.Jackson)
                .to(G1_SEARCH_FLIGHT_EP);
    }
}
