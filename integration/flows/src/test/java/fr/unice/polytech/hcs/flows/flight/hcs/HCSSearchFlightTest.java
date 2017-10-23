package fr.unice.polytech.hcs.flows.flight.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchResponse;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_FLIGHT_MQ;

public class HCSSearchFlightTest extends ActiveMQTest {


    @Override
    public String isMockEndpointsAndSkip() {
        return HCS_SEARCH_FLIGHT_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new HCSSearchFlight();
    }

    private FlightSearchRequest fsr;
    private HCSFlightSearchRequest hcsFsr;

    private FlightSearchResponse fsRes;

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

    @Before
    public void initMocks() {
        resetMocks();
        mock(HCS_SEARCH_FLIGHT_EP).whenAnyExchangeReceived(e -> e.getIn().setBody(fsResJson));
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

        hcsFsr = new HCSFlightSearchRequest(fsr);

        fsRes = new ObjectMapper().readValue(fsResJson, FlightSearchResponse.class);
    }


    @Test
    public void TestHCSSearchFlight() throws Exception {
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(HCS_SEARCH_FLIGHT_MQ));
        assertNotNull(context.hasEndpoint(HCS_SEARCH_FLIGHT_EP));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://" + HCS_SEARCH_FLIGHT_EP;
        assertNotNull(context.hasEndpoint(mock));

        // Check if we receive a message.
        getMockEndpoint(mock).expectedMessageCount(1);
        getMockEndpoint(mock).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("CamelHttpMethod", "POST");

        // The FSR request is sent to the message Queue !
        FlightSearchResponse out = template.requestBody(HCS_SEARCH_FLIGHT_MQ, fsr, FlightSearchResponse.class);

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mock).assertIsSatisfied();

        // Catch the data into the request catched in the Mock Ws
        String requestStr = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(hcsFsr), requestStr, false);

        // Check result
        assertEquals(fsRes, out);
    }


}
