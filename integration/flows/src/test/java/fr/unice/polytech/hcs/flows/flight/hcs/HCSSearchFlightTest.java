package fr.unice.polytech.hcs.flows.flight.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.SpecificSearchTest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchResponse;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_MQ;

public class HCSSearchFlightTest extends SpecificSearchTest {

    public HCSSearchFlightTest() {
        super(HCS_SEARCH_FLIGHT_EP, HCS_SEARCH_FLIGHT_MQ, new HCSSearchFlight());
    }

    private final String fsResJson = "{\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"origin\": \"Nice\",\n" +
            "      \"destination\": \"Paris\",\n" +
            "      \"date\": \"2017-08-14\",\n" +
            "      \"time\": \"12:30:00\",\n" +
            "      \"price\": \"89\",\n" +
            "      \"journeyType\": \"DIRECT\",\n" +
            "      \"duration\": 92,\n" +
            "      \"category\": \"ECO\",\n" +
            "      \"airline\": \"Air France\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"origin\": \"Nice\",\n" +
            "      \"destination\": \"Paris\",\n" +
            "      \"date\": \"2017-08-14\",\n" +
            "      \"time\": \"08:45:00\",\n" +
            "      \"price\": \"63\",\n" +
            "      \"journeyType\": \"DIRECT\",\n" +
            "      \"duration\": 105,\n" +
            "      \"category\": \"ECO\",\n" +
            "      \"airline\": \"EasyJet\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Override
    public void initVariables() throws Exception {
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

        this.genericRequest = fsr;
        this.genericResponse = new ObjectMapper().readValue(fsResJson, FlightSearchResponse.class);
        this.specificResultJson = fsResJson;
    }
}
