package fr.unice.polytech.hcs.flows.hotel.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

public class HCSSearchHotelTest extends ActiveMQTest {


    @Override
    public String isMockEndpointsAndSkip() {
        return Endpoints.HCS_SEARCH_HOTEL_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new HCSSearchHotel();
    }

    private HotelSearchRequest hotel;

    @Before
    public void init() {
        resetMocks();
        hotel = new HotelSearchRequest();
        hotel.city = "Paris";
        hotel.dateFrom = "2017-10-21";
        hotel.dateTo = "2017-10-26";
        hotel.order = "ASCENDING";
    }


    @Test
    public void TestSearchFlight() throws Exception {
        // Asserting endpoints existence
        assertNotNull(Endpoints.HCS_SEARCH_HOTEL_MQ + " no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_HOTEL_MQ));
        assertNotNull(Endpoints.HCS_SEARCH_HOTEL_EP + " no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_HOTEL_EP));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://" + Endpoints.HCS_SEARCH_HOTEL_EP;

        // Is the mock have endpoint ? Mandatory for the next step
        assertNotNull(context.hasEndpoint(mock));

        // check if we receive a message.
        getMockEndpoint(mock).expectedMessageCount(1);

        // Create the mapper to transform hotel -> String thanks to Serializable properties
        ObjectMapper mapper = new ObjectMapper();

        // The tesla asking is sended to the message Queue !
        template.sendBody(mock, mapper.writeValueAsString(hotel));

        // Catch the data into the request catched in the Mock Ws
        String request = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mock).assertIsSatisfied();

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        assertEquals(mapper.writeValueAsString(hotel), request);

    }


}
