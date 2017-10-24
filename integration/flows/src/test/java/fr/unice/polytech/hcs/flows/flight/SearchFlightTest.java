package fr.unice.polytech.hcs.flows.flight;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.SplittatorRouteTest;
import fr.unice.polytech.hcs.flows.flight.g1.G1SearchFlight;

import java.util.Arrays;
import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Build.*;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchFlightTest extends SplittatorRouteTest<Flight> {

    public SearchFlightTest() {
        super(
                Arrays.asList(
                        HCS_SEARCH_FLIGHT_MQ,
                        G1_SEARCH_FLIGHT_MQ),
                SEARCH_FLIGHT_MQ,
                new SearchFlight());
    }

    private final String HCSfsResJson = "{\n" +
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

    private final String G1fsResJson = "{\n" +
            "    \"flights\": [\n" +
            "        {\n" +
            "            \"departure\": 710171030,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 66,\n" +
            "            \"arrival\": 710171730,\n" +
            "            \"seatClass\": \"Green\",\n" +
            "            \"price\": 1188.39,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 700\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710171130,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 49,\n" +
            "            \"arrival\": 710171630,\n" +
            "            \"seatClass\": \"Aquamarine\",\n" +
            "            \"price\": 1662.52,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 500\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710170930,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 91,\n" +
            "            \"arrival\": 710171530,\n" +
            "            \"seatClass\": \"Orange\",\n" +
            "            \"price\": 1449.74,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 600\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710171530,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 20,\n" +
            "            \"arrival\": 710172030,\n" +
            "            \"seatClass\": \"Maroon\",\n" +
            "            \"price\": 537.02,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 500\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710170930,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 81,\n" +
            "            \"arrival\": 710171530,\n" +
            "            \"seatClass\": \"Purple\",\n" +
            "            \"price\": 1554.62,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 600\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710171730,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 76,\n" +
            "            \"arrival\": 710172230,\n" +
            "            \"seatClass\": \"Orange\",\n" +
            "            \"price\": 1326.6,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 500\n" +
            "        }\n" +
            "    ]\n" +
            "}";

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

        Flight expected = new Flight();
        expected.origin = "Nice";
        expected.destination = "Paris";
        expected.date = "2017-08-14";
        expected.time = "08:45:00";
        expected.price = 63.00;
        expected.journeyType = "DIRECT";
        expected.duration = 105;
        expected.category = "ECO";
        expected.airline = "EasyJet";

        this.genericRequest = fsr;
        this.mockedEpResults = map(
                entry(HCS_SEARCH_FLIGHT_MQ, HCSfsRes),
                entry(G1_SEARCH_FLIGHT_MQ, G1fsRes));
        this.expectedResultClass = Flight.class;
        this.expectedResult = expected;
    }
}
