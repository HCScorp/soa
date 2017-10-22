package fr.unice.polytech.hcs.flow.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flow.ActiveMQTest;
import fr.unice.polytech.hcs.flows.hcs.flight.Flight;
import fr.unice.polytech.hcs.flows.hcs.flight.SearchFlight;
import fr.unice.polytech.hcs.flows.hcs.hotel.HotelRequest;
import fr.unice.polytech.hcs.flows.hcs.hotel.SearchHotel;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

public class SearchHotelTest extends ActiveMQTest {


    @Override public String isMockEndpointsAndSkip() { return Endpoints.HCS_SEARCH_HOTEL_EP; }
    @Override protected RouteBuilder createRouteBuilder() throws Exception { return new SearchHotel(); }

    private HotelRequest hotel;

    @Before
    public void init(){
        resetMocks();
        hotel = new HotelRequest();
        hotel.setCity("Paris");
        hotel.setDateFrom("2017-10-21");
        hotel.setDateTo("2017-10-26");
        hotel.setOrder("ASCENDING");
    }


    @Test
    public void TestSearchFlight() throws Exception{
        // Asserting endpoints existence
        assertNotNull(Endpoints.HCS_SEARCH_HOTEL_MQ + " no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_HOTEL_MQ));
        assertNotNull(Endpoints.HCS_SEARCH_HOTEL_EP + " no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_HOTEL_EP));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://"+ Endpoints.HCS_SEARCH_HOTEL_EP;

        // Is the mock have endpoint ? Mandatory for the next step
        assertNotNull(context.hasEndpoint(mock));

        // check if we receive a message.
        getMockEndpoint(mock).expectedMessageCount(1);

        // Create the mapper to transform Tesla -> String thanks to Serializable properties
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
