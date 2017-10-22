package fr.unice.polytech.hcs.flows.flight.g1;


import fr.unice.polytech.hcs.flows.flight.Flight;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G1_SEARCH_FLIGHT_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G1_SEARCH_FLIGHT_MQ;

public class G1SearchFlight extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(G1_SEARCH_FLIGHT_MQ)
                .routeId("g1-search-flight-ws")
                .routeDescription("Send the flight search request to the flight WS")

                .log("Create request for G1 flight WS ")
                .process(e -> {
                    G1FlightSearchRequest req = new G1FlightSearchRequest((FlightSearchRequest) e.getIn().getBody());
                    e.getIn().setBody(req);
                })

                .log("${body.origin} -> ${body.destination} Marshalling request")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))

                .log("Marshalling into a JSON body")
                .marshal().json(JsonLibrary.Jackson)
                .inOut(G1_SEARCH_FLIGHT_EP)
                .unmarshal(new JsonDataFormat())
                .process(jsonToFlightSearchResponse)
        ;
    }

    private static Processor jsonToFlightSearchResponse = e -> {
        Collection<Map<String, Object>> flights = (Collection<Map<String, Object>>) ((Map<String, Object>) e.getIn().getBody()).get("flights");

        FlightSearchResponse fsr = new FlightSearchResponse();
        fsr.flights = (Flight[]) flights.stream()
                .map(m -> {
                    Flight f = new Flight();
                    f.origin = (String) m.get("from");
                    f.destination = (String) m.get("to");
                    Date departure = new Date((long) m.get("departure"));
                    LocalDateTime dateTime = LocalDateTime.ofInstant(departure.toInstant(), ZoneId.systemDefault());
                    f.date = dateTime.toLocalDate().toString();
                    f.time = dateTime.toLocalTime().toString();
                    f.price = (float) m.get("price");
                    f.journeyType = "UNKNOWN";
                    f.duration = (int) m.get("duration");
                    f.category = (String) m.get("seatClass");
                    f.airline = (String) m.get("airline");
                    return f;
                })
                .collect(Collectors.toList())
                .toArray();


        e.getIn().setBody(fsr);
    };
}
