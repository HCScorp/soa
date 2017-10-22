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
        request.setOrigin("Nice");
        request.setDestination("Paris");
        request.setDate("2017-10-21");
        request.setTimeFrom("08-30-00");
        request.setTimeTo("12-30-00");
        request.setJourneyType("DIRECT");
        request.setMaxTravelTime(120);
        request.setCategory("BUSINESS");
        request.setAirline("AirFrance");
        request.setOrder("ASCENDING");

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

        // The tesla asking is sended to the message Queue !
        template.sendBody(mock, mapper.writeValueAsString(request));

        // Catch the data into the request catched in the Mock Ws
        String request = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mock).assertIsSatisfied();

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        assertEquals(mapper.writeValueAsString(hcs), request);

    }


}
