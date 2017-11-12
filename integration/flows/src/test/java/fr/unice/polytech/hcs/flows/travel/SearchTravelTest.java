package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.GET_TRAVEL;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.SEARCH_TRAVEL;

public class SearchTravelTest extends ActiveMQTest
{
    private final String mockedEndpoint;
    private final String target;
    private final RouteBuilder routeBuilder;

    protected Serializable request;
    protected String response;

    public SearchTravelTest() {
        this.mockedEndpoint = GET_TRAVEL;
        this.target = SEARCH_TRAVEL;
        this.routeBuilder = new SearchTravel();
    }

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
        resetMocks();

        Travel travel = new Travel();
        travel.travelId = "azerty";
        travel.status = "Done";
        travel.documents = new ArrayList<>();

        Expense expense = new Expense();
        expense.category = "restaurant";
        expense.evidence = "restaurant.jpg";
        expense.price = 25.3;

        travel.documents.add(expense);

        response = new ObjectMapper().writeValueAsString(travel);

        mock(mockedEndpoint).whenAnyExchangeReceived(e -> e.getIn().setBody(travel));
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
        String out = template.requestBody(target, request, response.getClass());

        // Assert that everything went as expected
        getMockEndpoint(mock).assertIsSatisfied();

        // Check result value
        assertEquals(response, out);
    }
}