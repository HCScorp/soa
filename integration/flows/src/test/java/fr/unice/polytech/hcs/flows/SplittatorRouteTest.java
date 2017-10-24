package fr.unice.polytech.hcs.flows;

import fr.unice.polytech.hcs.flows.splitator.GenericResponse;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SplittatorRouteTest<T extends Serializable> extends ActiveMQTest {

    private final Collection<String> mockedEp;
    private final String target;
    private final RouteBuilder routeBuilder;

    protected Map<String, GenericResponse<T>> mockedEpResults;
    protected Serializable genericRequest;
    protected T expectedResult;
    protected Class<T> expectedResultClass;

    public SplittatorRouteTest(Collection<String> mockedEp,
                               String target,
                               RouteBuilder routeBuilder) {
        this.mockedEp = mockedEp;
        this.target = target;
        this.routeBuilder = routeBuilder;
    }

    protected abstract void initVariables() throws Exception;

    @Override
    public String isMockEndpointsAndSkip() {
        return mockedEp.stream().collect(Collectors.joining("|"));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return routeBuilder;
    }

    @Before
    public void initMocks() {
        resetMocks();
        mockedEpResults.forEach(
                (endPoint, result) -> mock(endPoint).whenAnyExchangeReceived(e -> e.getIn().setBody(result)));
    }

    @Before
    public void init() throws Exception {
        initVariables();
    }

    @Test
    public void TestSearch() throws Exception {
        List<String> mocks = mockedEp.stream()
                .map(endPoint -> {
                    // Asserting endpoints existence
                    assertNotNull(context.hasEndpoint(endPoint));
                    final String mock = "mock://" + endPoint;
                    // Configuring expectations on the mocked endpoint
                    assertNotNull(context.hasEndpoint(mock));
                    // Check if we receive a message.
                    getMockEndpoint(mock).expectedMessageCount(1);
                    return mock;
                })
                .collect(Collectors.toList());

        // The generic request is sent to the target
        T out = template.requestBody(target, genericRequest, expectedResultClass);

        // Do I receive the proper request ? (type, post, ... )
        for (String mock : mocks) {
            getMockEndpoint(mock).assertIsSatisfied();
        }

        // Check result
        assertEquals(expectedResult, out);
    }
}