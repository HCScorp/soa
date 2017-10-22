package fr.unice.polytech.hcs.flow.flight.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flow.ActiveMQTest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.g1.G1FlightSearchRequest;
import fr.unice.polytech.hcs.flows.flight.g1.G1SearchFlight;
import fr.unice.polytech.hcs.flows.flight.hcs.HCSSearchFlight;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

public class G1SearchFlightTest extends ActiveMQTest {

    @Override
    public String isMockEndpointsAndSkip() {
        return Endpoints.G1_SEARCH_FLIGHT_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new G1SearchFlight();
    }

    G1FlightSearchRequest flight;

    G1FlightSearchRequest request;

    @Before
    public void init() {
        FlightSearchRequest f = new FlightSearchRequest();
        f.date = "2017-01-05";
        f.airline = "airfrance";
        f.category = "airbus";
        f.destination = "caraibe";
        f.journeyType = "normal";
        f.order = "10";
        f.timeFrom = "08-00-00" ;
        f.timeTo = "14-00-00";
        f.maxTravelTime = 20;
        flight = new G1FlightSearchRequest(f);
        request = new G1FlightSearchRequest(f);
    }

    @Test
    public void TestG1SearchFlight() throws Exception {
        // Asserting endpoints existence
        assertNotNull(Endpoints.G1_SEARCH_FLIGHT_EP + " no endpoint !", context.hasEndpoint(Endpoints.G1_SEARCH_FLIGHT_EP));
        assertNotNull(Endpoints.G1_SEARCH_FLIGHT_MQ + " no endpoint !", context.hasEndpoint(Endpoints.G1_SEARCH_FLIGHT_MQ));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://" + Endpoints.G1_SEARCH_FLIGHT_EP;

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
        assertEquals(mapper.writeValueAsString(flight), request);

    }


}
