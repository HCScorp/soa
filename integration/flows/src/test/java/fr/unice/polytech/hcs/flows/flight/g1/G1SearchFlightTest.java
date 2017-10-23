package fr.unice.polytech.hcs.flows.flight.g1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchResponse;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class G1SearchFlightTest extends ActiveMQTest {

    @Override
    public String isMockEndpointsAndSkip() {
        return G1_SEARCH_FLIGHT_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new G1SearchFlight();
    }

    private FlightSearchRequest fsr;
    private G1FlightSearchRequest g1Fsr;

    private FlightSearchResponse fsRes;

    private final String fsResJson = "{\n" +
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
        mock(G1_SEARCH_FLIGHT_EP).whenAnyExchangeReceived(e -> e.getIn().setBody(fsResJson));
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

        g1Fsr = new G1FlightSearchRequest(fsr);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> fsResMap = mapper.readValue(fsResJson, typeRef);
        fsRes = G1SearchFlight.mapToFsRes(fsResMap);
    }

    @Test
    public void TestG1SearchFlight() throws Exception {
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(G1_SEARCH_FLIGHT_MQ));
        assertNotNull(context.hasEndpoint(G1_SEARCH_FLIGHT_EP));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://" + G1_SEARCH_FLIGHT_EP;
        assertNotNull(context.hasEndpoint(mock));

        // Check if we receive a message.
        getMockEndpoint(mock).expectedMessageCount(1);
        getMockEndpoint(mock).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("CamelHttpMethod", "POST");

        // The FSR request is sent to the message Queue !
        FlightSearchResponse out = template.requestBody(G1_SEARCH_FLIGHT_MQ, fsr, FlightSearchResponse.class);

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mock).assertIsSatisfied();

        // Catch the data into the request catched in the Mock Ws
        String requestStr = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(g1Fsr), requestStr, false);

        // Check result
        assertEquals(fsRes, out);
    }


}
