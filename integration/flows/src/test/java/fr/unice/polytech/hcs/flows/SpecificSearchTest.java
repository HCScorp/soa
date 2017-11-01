package fr.unice.polytech.hcs.flows;

import fr.unice.polytech.hcs.flows.splitator.GenericResponse;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

public abstract class SpecificSearchTest extends ActiveMQTest {

    private final String mockedEndpoint;
    private final String target;
    private final RouteBuilder routeBuilder;

    protected Serializable genericRequest;
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
    public void init() throws Exception {
        initVariables();
        resetMocks();
        mock(mockedEndpoint).whenAnyExchangeReceived(e -> e.getIn().setBody(specificResultJson));
    }

    @Test
    public void TestSearch() throws Exception {
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(target));
        isAvailableAndMocked(mockedEndpoint);

        // Configuring expectations on the mocked endpoint
        String mock = mockStr(mockedEndpoint);
        // Check if we receive a message.
        getMockEndpoint(mock).expectedMessageCount(1);

        // Send the generic request to the target
        GenericResponse out = template.requestBody(target, genericRequest, genericResponse.getClass());

        // Assert that everything went as expected
        getMockEndpoint(mock).assertIsSatisfied();

        // Check result value
        assertEquals(genericResponse, out);
    }
}
