package fr.unice.polytech.hcs.flows.flight.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.SimplePostJsonRoute;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_MQ;

public class HCSSearchFlight extends SimplePostJsonRoute<FlightSearchRequest, FlightSearchResponse> {

    public HCSSearchFlight() {
        super(HCS_SEARCH_FLIGHT_MQ,
                HCS_SEARCH_FLIGHT_EP,
                HCSFlightSearchRequest::new,
                fsRes -> new ObjectMapper().convertValue(fsRes, FlightSearchResponse.class),
                "hcs-search-flight-ws",
                "Send the flight search request to the flight WS");
    }
}
