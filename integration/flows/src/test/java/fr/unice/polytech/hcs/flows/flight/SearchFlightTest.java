package fr.unice.polytech.hcs.flows.flight;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.flight.g1.G1SearchFlight;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchFlightTest extends ActiveMQTest {
    @Override
    public String isMockEndpointsAndSkip() {
        return HCS_SEARCH_FLIGHT_MQ + "|" + G1_SEARCH_FLIGHT_MQ;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new SearchFlight();
    }

    private FlightSearchRequest fsr;

    private FlightSearchResponse HCSfsRes;
    private FlightSearchResponse G1fsRes;

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

    @Before
    public void initMocks() {
        resetMocks();
        mock(HCS_SEARCH_FLIGHT_MQ).whenAnyExchangeReceived(e -> e.getIn().setBody(HCSfsRes));
        mock(G1_SEARCH_FLIGHT_MQ).whenAnyExchangeReceived(e -> e.getIn().setBody(G1fsRes));
    }

    @Before
    public void init() throws IOException {
        fsr = new FlightSearchRequest();
        fsr.origin = "Nice";
        fsr.destination = "Paris";
        fsr.date = "2017-01-05";
        fsr.airline = "Air France";
        fsr.category = "ECO";
        fsr.journeyType = "DIRECT";
        fsr.order = "ASCENDING";
        fsr.timeFrom = "08:00:00" ;
        fsr.timeTo = "14:30:00";
        fsr.maxTravelTime = 200;

        ObjectMapper mapper = new ObjectMapper();
        HCSfsRes = mapper.readValue(HCSfsResJson, FlightSearchResponse.class);

        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
        HashMap<String,Object> fsResMap = mapper.readValue(G1fsResJson, typeRef);
        G1fsRes = G1SearchFlight.mapToFsRes(fsResMap);
    }


    @Test
    public void TestSearchFlight() throws Exception {
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(HCS_SEARCH_FLIGHT_MQ));
        assertNotNull(context.hasEndpoint(G1_SEARCH_FLIGHT_MQ));

        // Configuring expectations on the mocked endpoint
        String mockHCS = "mock://" + HCS_SEARCH_FLIGHT_MQ;
        String mockG1 = "mock://" + G1_SEARCH_FLIGHT_MQ;
        assertNotNull(context.hasEndpoint(mockHCS));
        assertNotNull(context.hasEndpoint(mockG1));

        // Check if we receive a message.
        getMockEndpoint(mockHCS).expectedMessageCount(1);
        getMockEndpoint(mockG1).expectedMessageCount(1);

        // The FSR request is sent to the message Queue !
        Flight out = template.requestBody(SEARCH_FLIGHT_MQ, fsr, Flight.class);

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mockHCS).assertIsSatisfied();
        getMockEndpoint(mockG1).assertIsSatisfied();

        // Check result
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

        assertEquals(expected, out);
    }
}
