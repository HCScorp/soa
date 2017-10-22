package fr.unice.polytech.hcs.flow.flight.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flow.ActiveMQTest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.hcs.HCSFlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.hcs.HCSSearchFlight;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

public class HCSSearchFlightTest extends ActiveMQTest {


    @Override
    public String isMockEndpointsAndSkip() {
        return Endpoints.HCS_SEARCH_FLIGHT_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new HCSSearchFlight();
    }

    private FlightSearchRequest request;
    private HCSFlightSearchRequest hcs;

    @Before
    public void init() {
        resetMocks();
        request = new FlightSearchRequest();
        request.origin = "Nice";
        request.destination = "Paris";
        request.date = "2017-10-21";
        request.timeFrom = "08-30-00";
        request.timeTo = "12-30-00";
        request.journeyType = "DIRECT";
        request.maxTravelTime = 120;
        request.category = "BUSINESS";
        request.airline = "AirFrance";
        request.order = "ASCENDING";

        hcs = new HCSFlightSearchRequest(request);
    }


    @Test
    public void TestSearchFlight() throws Exception {
        // Asserting endpoints existence
        assertNotNull(Endpoints.HCS_SEARCH_FLIGHT_MQ + " no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_FLIGHT_MQ));
        assertNotNull(Endpoints.HCS_SEARCH_FLIGHT_EP + " no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_FLIGHT_EP));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://" + Endpoints.HCS_SEARCH_FLIGHT_EP;

        // Is the mock have endpoint ? Mandatory for the next step
        assertNotNull(context.hasEndpoint(mock));

        // check if we receive a message.
        getMockEndpoint(mock).expectedMessageCount(1);

        // Create the mapper to transform Tesla -> String thanks to Serializable properties
        ObjectMapper mapper = new ObjectMapper();

        // The tesla request is sent to the message Queue !
        template.sendBody(mock, mapper.writeValueAsString(request));

        // Catch the data into the request catched in the Mock Ws
        String request = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mock).assertIsSatisfied();

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        assertEquals(mapper.writeValueAsString(hcs), request);

    }


}
