package fr.unice.polytech.hcs.flows.flight.g1;


import fr.unice.polytech.hcs.flows.flight.Flight;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.SimplePostJsonRoute;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G1_SEARCH_FLIGHT_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G1_SEARCH_FLIGHT_MQ;

public class G1SearchFlight extends SimplePostJsonRoute<FlightSearchRequest, FlightSearchResponse> {

    public G1SearchFlight() {
        super(G1_SEARCH_FLIGHT_MQ,
                G1_SEARCH_FLIGHT_EP,
                G1FlightSearchRequest::new,
                G1SearchFlight::mapToFsRes,
                "g1-search-flight-ws",
                "Send the flight search request to the flight WS");
    }

    public static FlightSearchResponse mapToFsRes(Map<String, Object> map) {
        Collection<Map<String, Object>> flights = (Collection<Map<String, Object>>) map.get("flights");

        FlightSearchResponse fsr = new FlightSearchResponse();
        fsr.result = new Flight[flights.size()];
        int i = 0;
        for (Map<String, Object> m : flights) {
            Flight f = new Flight();
            f.origin = (String) m.get("from");
            f.destination = (String) m.get("to");
            Date departure = new Date((Integer) m.get("departure"));
            LocalDateTime dateTime = LocalDateTime.ofInstant(departure.toInstant(), ZoneId.systemDefault());
            f.date = dateTime.toLocalDate().toString();
            f.time = dateTime.toLocalTime().toString();

            // Thanks camel unmarshalling
            Object p = m.get("price");
            if (p instanceof Double) {
                f.price = (Double) p;
            } else if (p instanceof Integer) {
                f.price = ((Integer) p).doubleValue();
            }

            f.journeyType = "UNKNOWN";
            f.duration = (Integer) m.get("duration");
            f.category = (String) m.get("seatClass");
            f.airline = (String) m.get("airline");
            fsr.result[i++] = f;
        }

        return fsr;
    }
}
