package fr.unice.polytech.hcs.flow.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flow.ActiveMQTest;
import fr.unice.polytech.hcs.flows.hcs.car.Car;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import fr.unice.polytech.hcs.flows.hcs.car.SearchCar;
import org.junit.Before;
import org.junit.Test;
import org.omg.IOP.ENCODING_CDR_ENCAPS;

public class SearchCarTest extends ActiveMQTest {

    @Override public String isMockEndpoints() { return Endpoints.HCS_SEARCH_CAR_EP + "|" + Endpoints.UNKNOWN_SEARCH_CAR_EP; }
    @Override protected RouteBuilder createRouteBuilder() throws Exception { return new SearchCar(); }

    private Car tesla;

    @Before
    public void init(){
        tesla = new Car();
        tesla.setCity("toulon");
        tesla.setDateTo("12-01-02");
        tesla.setDateFrom("11-01-02");
    }

    @Test
    public void TestSearchFlightHcs() throws Exception{
        // Asserting endpoints existence
        assertNotNull(Endpoints.HCS_SEARCH_CAR_EP + "no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_CAR_MQ));
        assertNotNull(Endpoints.HCS_SEARCH_CAR_MQ + "no endpoint !",context.hasEndpoint(Endpoints.HCS_SEARCH_CAR_EP));
        assertNotNull(Endpoints.UNKNOWN_SEARCH_CAR_EP + "no endpoint !",context.hasEndpoint(Endpoints.HCS_SEARCH_CAR_EP));

        // Configuring expectations on the mocked endpoint
        String mockHcs = "mock://"+ Endpoints.HCS_SEARCH_CAR_EP;
        String mockUnknown = "mock://"+ Endpoints.UNKNOWN_SEARCH_CAR_EP;

        // Is the mock have endpoint ? Mandatory for the next step
        assertNotNull(context.hasEndpoint(mockHcs));
        assertNotNull(context.hasEndpoint(mockUnknown));

        // check if we receive a message.
        getMockEndpoint(mockHcs).expectedMessageCount(1);
        getMockEndpoint(mockUnknown).expectedMessageCount(1);

        // Create the mapper to transform Tesla -> String thanks to Serializable properties
        ObjectMapper mapper = new ObjectMapper();

        // The tesla asking is sended to the message Queue !
        template.sendBody(mockHcs, mapper.writeValueAsString(tesla));

        // Catch the data into the request catched in the Mock Ws
        String request = getMockEndpoint(mockHcs).getReceivedExchanges().get(0).getIn().getBody(String.class);

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mockHcs).assertIsSatisfied();
        getMockEndpoint(mockUnknown).assertIsSatisfied();
        // As the assertions are now satisfied, one can access to the contents of the exchanges
        assertEquals(mapper.writeValueAsString(tesla), request);

    }


}
