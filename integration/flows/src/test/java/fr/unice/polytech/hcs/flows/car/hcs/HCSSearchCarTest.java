package fr.unice.polytech.hcs.flows.car.hcs;

import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

public class HCSSearchCarTest extends ActiveMQTest {

    @Override
    public String isMockEndpoints() {
        return Endpoints.HCS_SEARCH_CAR_EP + "|" + Endpoints.G2_SEARCH_CAR_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new HCSSearchCar();
    }

    private Car tesla;

    @Before
    public void init() {
        tesla = new Car();
        tesla.setCity("toulon");
        tesla.setDateTo("12-01-02");
        tesla.setDateFrom("11-01-02");
    }

    @Test
    public void TestHCSSearchCar() throws Exception {
//        // Asserting endpoints existence
//        assertNotNull(Endpoints.HCS_SEARCH_CAR_EP + "no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_CAR_MQ));
//        assertNotNull(Endpoints.HCS_SEARCH_CAR_MQ + "no endpoint !", context.hasEndpoint(Endpoints.HCS_SEARCH_CAR_EP));
//        assertNotNull(Endpoints.G2_SEARCH_CAR_EP + "no endpoint !", context.hasEndpoint(Endpoints.G2_SEARCH_CAR_EP));
//
//        // Configuring expectations on the mocked endpoint
//        String mockHCS = "mock://" + Endpoints.HCS_SEARCH_CAR_EP;
//        String mockG2 = "mock://" + Endpoints.G2_SEARCH_CAR_EP;
//
//
//        // Is the mock have endpoint ? Mandatory for the next step
//        assertNotNull(context.hasEndpoint(mockHCS));
//        assertNotNull(context.hasEndpoint(mockG2));
//
//        // check if we receive a message.
//        getMockEndpoint(mockHCS).expectedMessageCount(1);
//        getMockEndpoint(mockG2).expectedMessageCount(1);
//
//        // Create the mapper to transform Tesla -> String thanks to Serializable properties
//        ObjectMapper mapper = new ObjectMapper();
//
//        // The tesla asking is sended to the message Queue !
//        template.sendBody(mockHCS, mapper.writeValueAsString(tesla));
//
//        // Catch the data into the request catched in the Mock Ws
//        String request = getMockEndpoint(mockHCS).getReceivedExchanges().get(0).getIn().getBody(String.class);
//
//        // Do I receive the proper request ? (type, post, ... )
//        getMockEndpoint(mockHCS).assertIsSatisfied();
//        getMockEndpoint(mockG2).assertIsSatisfied();
//        // As the assertions are now satisfied, one can access to the contents of the exchanges
//
//        assertEquals(mapper.writeValueAsString(tesla), request);

    }


}
