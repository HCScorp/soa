package fr.unice.polytech.hcs.flows;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.splitator.GenericResponse;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.Serializable;

public abstract class SpecificSearchTest extends ActiveMQTest {

    private final String mockedEndpoint;
    private final String target;
    private final RouteBuilder routeBuilder;

    protected Serializable genericRequest;
    protected Serializable specificRequest;
    protected GenericResponse genericResponse;
    protected String specificResultJson;

    public SpecificSearchTest(String mockedEndpoint, String target, RouteBuilder routeBuilder) {
        this.mockedEndpoint = mockedEndpoint;
        this.target = target;
        this.routeBuilder = routeBuilder;
    }

    public abstract void initVariables() throws Exception;

    @Override
    public String isMockEndpointsAndSkip() {
        return mockedEndpoint;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return routeBuilder;
    }

    @Before
    public void initMocks() {
        resetMocks();
        mock(mockedEndpoint).whenAnyExchangeReceived(e -> e.getIn().setBody(specificResultJson));
    }

    @Before
    public void init() throws Exception {
        initVariables();
    }

    @Test
    public void TestSearch() throws Exception {
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(target));
        assertNotNull(context.hasEndpoint(mockedEndpoint));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://" + mockedEndpoint;
        assertNotNull(context.hasEndpoint(mock));

        // Check if we receive a message.
        getMockEndpoint(mock).expectedMessageCount(1);
        getMockEndpoint(mock).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("CamelHttpMethod", "POST");

        // The generic request is sent to the target
        GenericResponse out = template.requestBody(target, genericRequest, genericResponse.getClass());

        // Do I receive the proper request ? (type, post, ... )
        getMockEndpoint(mock).assertIsSatisfied();

        // Catch the data into the request catched in the Mock Ws
        String requestStr = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(specificRequest), requestStr, false);

        // Check result
        assertEquals(genericResponse, out);
    }
}
