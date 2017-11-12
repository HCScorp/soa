package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Travel;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ApproveTravelTest extends ActiveMQTest
{
    private final Collection<String> mockedEp;
    private final Map<String, Object> mockedEpResults;

    private final String target;
    private final RouteBuilder routeBuilder;

    private Travel travel;
    private Travel travelManual;

    public ApproveTravelTest() {
        this.mockedEp = Arrays.asList(GET_TRAVEL, UPDATE_TRAVEL, REFUND_SENDING);
        this.mockedEpResults = new HashMap<>();
        this.target = Endpoints.END_TRAVEL;
        this.routeBuilder = new ApproveTravel();
    }

    @Override
    public String isMockEndpointsAndSkip() {
        return mockedEp.stream().collect(Collectors.joining("|"));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return routeBuilder;
    }

    protected void initVariables() throws Exception {
        travel = new Travel();
        travel.travelId = "azerty";
        travel.status = "Done";
        travel.documents = new ArrayList<>();

        Expense expense = new Expense();
        expense.category = "restaurant";
        expense.evidence = "restaurant.jpg";
        expense.price = 25.6;
        travel.documents.add(expense);

        mockedEpResults.put(GET_TRAVEL, travel);
    }

    @Before
    public void init() throws Exception {
        initVariables();
        resetMocks();
        mockedEpResults.forEach(
                (endPoint, result) -> this.mock(endPoint).whenAnyExchangeReceived(e -> e.getIn().setBody(result)));
    }

    @Test
    public void TestSearchAccepted() throws Exception {
        List<String> mocks = mockedEp.stream()
                .map(endPoint -> {
                    // Asserting endpoints existence
                    isAvailableAndMocked(endPoint);

                    // Configuring expectations on the mocked endpoint
                    final String mock = mockStr(endPoint);
                    // Check if we receive a message.
                    getMockEndpoint(mock).expectedMessageCount(1);
                    return mock;
                })
                .collect(Collectors.toList());

        Expense expense = new Expense();
        expense.category = "restaurant";
        expense.evidence = "restaurant.jpg";
        expense.price = 25.6;

        travel.documents.add(expense);

        // Send null because first operation is to load travel from db -> mocked
        String outStr = template.requestBody(target, null, String.class);
        Map out = new ObjectMapper().readValue(outStr, Map.class);

        // Do I receive the proper request ? (type, post, ... )
        for (String mock : mocks) {
            getMockEndpoint(mock).assertIsSatisfied();
        }

        Map<String, Object> expected = new HashMap<>();
        expected.put("message", "Refund accepted.");
        expected.put("status", "ok");

        // Check result
        assertEquals(expected, out);
    }

    @Test
    public void TestSearchManual() throws Exception {
    List<String> mocks = mockedEp.stream()
            .map(endPoint -> {
                // Asserting endpoints existence
                isAvailableAndMocked(endPoint);

                // Configuring expectations on the mocked endpoint
                final String mock = mockStr(endPoint);
                // Check if we receive a message.
                getMockEndpoint(mock).expectedMessageCount(1);
                return mock;
            })
            .collect(Collectors.toList());

    Expense expense = new Expense();
    expense.category = "restaurant";
    expense.evidence = "restaurant.jpg";
    expense.price = 200.6;

    travel.documents.add(expense);

    // Send null because first operation is to load travel from db -> mocked
    String outStr = template.requestBody(target, null, String.class);
    Map out = new ObjectMapper().readValue(outStr, Map.class);

    // Do I receive the proper request ? (type, post, ... )
    getMockEndpoint(mocks.get(0)).assertIsSatisfied();
    getMockEndpoint(mocks.get(1)).assertIsSatisfied();

    Map<String, Object> expected = new HashMap<>();
    expected.put("message", "You need to justify your budget overrun.");
    expected.put("status", "pending");

    // Check result
    assertEquals(expected, out);
}
}