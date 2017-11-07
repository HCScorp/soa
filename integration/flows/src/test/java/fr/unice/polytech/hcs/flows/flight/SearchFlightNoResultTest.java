package fr.unice.polytech.hcs.flows.flight;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.SplittatorRouteTest;
import fr.unice.polytech.hcs.flows.flight.g1.G1SearchFlight;

import java.util.Arrays;
import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Build.entry;
import static fr.unice.polytech.hcs.flows.utils.Build.map;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchFlightNoResultTest extends SplittatorRouteTest<Flight> {

    public SearchFlightNoResultTest() {
        super(
                Arrays.asList(
                        HCS_SEARCH_FLIGHT_MQ,
                        G1_SEARCH_FLIGHT_MQ),
                SEARCH_FLIGHT_MQ,
                new SearchFlight());
    }

    private final String HCSfsResJson = "{\"result\": []}";

    private final String G1fsResJson = "{\"flights\": []}";

    @Override
    protected void initVariables() throws Exception {
        FlightSearchResponse HCSfsRes;
        FlightSearchResponse G1fsRes;

        FlightSearchRequest fsr = new FlightSearchRequest();
        fsr.origin = "Nice";
        fsr.destination = "Paris";
        fsr.date = "2017-01-05";
        fsr.airline = "Air France";
        fsr.category = "ECO";
        fsr.journeyType = "DIRECT";
        fsr.order = "ASCENDING";
        fsr.timeFrom = "08:00:00";
        fsr.timeTo = "14:30:00";
        fsr.maxTravelTime = 200;

        ObjectMapper mapper = new ObjectMapper();
        HCSfsRes = mapper.readValue(HCSfsResJson, FlightSearchResponse.class);

        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        HashMap<String, Object> fsResMap = mapper.readValue(G1fsResJson, typeRef);
        G1fsRes = G1SearchFlight.mapToFsRes(fsResMap);

        this.genericRequest = fsr;
        this.mockedEpResults = map(
                entry(HCS_SEARCH_FLIGHT_MQ, HCSfsRes),
                entry(G1_SEARCH_FLIGHT_MQ, G1fsRes));
        this.expectedResultClass = Flight.class;
        this.expectedResult = null;
    }
}
