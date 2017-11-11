package fr.unice.polytech.hcs.flows.expenses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.ExpenseRegistration;
import fr.unice.polytech.hcs.flows.expense.Status;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.io.Serializable;
import java.util.ArrayList;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.EXPENSE_DATABASE;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.EXPENSE_EMAIL;

public class ExpenseRegistrationTest  extends CamelTestSupport {

    private final String mockedEndpoint;
    private final String target;
    private final RouteBuilder routeBuilder;

    private Serializable request;
    private Travel travelResponse;

    private MongoClient mockClient;
    private DB mockDB;
    private Fongo fongo;

    private final String specificResultJson = "{\n" +
            "    \"travelId\": \"100\",\n" +
            "    \"status\": \"DONE\",\n" +
            "    \"documents\": [{\n" +
            "        \"category\": \"restaurant\",\n" +
            "        \"evidence\": \"restaurant.jpg\",\n" +
            "        \"price\": 25\n" +
            "    }]\n" +
            "}";

    public ExpenseRegistrationTest() {
        this.mockedEndpoint = "mock://" + EXPENSE_EMAIL;
        this.target = EXPENSE_DATABASE;
        this.routeBuilder = new ExpenseRegistration();
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {

        fongo = new Fongo("database");
        mockDB = fongo.getDB("expense");

        mockClient = PowerMockito.mock(MongoClient.class);

        PowerMockito.when(mockClient.getDB(Mockito.anyString()))
                .thenReturn(mockDB);

        PowerMockito.whenNew(MongoClient.class).withAnyArguments().thenReturn(mockClient);

        JndiRegistry jndi = super.createRegistry();

        jndi.bind("myDb", mockClient);
        return jndi;
    }


    @Override
    public String isMockEndpointsAndSkip() {
        return mockedEndpoint;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return routeBuilder;
    }

    public void initVariables() throws Exception {
        Travel travel = new Travel();
        travel.travelId = "100";
        travel.status = Status.DONE;
        travel.documents = new ArrayList<>();

        Expense expense = new Expense();
        expense.category = "restaurant";
        expense.evidence = "restaurant.jpg";
        expense.price = 25;

        travel.documents.add(expense);

        this.request = travel;
        this.travelResponse = new ObjectMapper().readValue(specificResultJson, Travel.class);
    }

    @Before
    public void init() throws Exception {
        initVariables();
        resetMocks();

        getMockEndpoint(mockedEndpoint).whenAnyExchangeReceived(e -> e.getIn().setBody(specificResultJson));
    }

    @Test
    public void TestSearch() throws Exception {
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(target));
        assertNotNull(context.hasEndpoint(mockedEndpoint));

        // Configuring expectations on the mocked endpoint
        String mock = mockedEndpoint;

        // Send the travel request to the target
        WriteResult out = template.requestBody(target, request, WriteResult.class);

        // Assert that everything went as expected
        getMockEndpoint(mock).assertIsSatisfied();

        assertEquals(1, out.getN());
    }
}