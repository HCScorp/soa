package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import com.mongodb.*;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.ExpenseRegistration;
import fr.unice.polytech.hcs.flows.expense.Status;
import fr.unice.polytech.hcs.flows.expense.Travel;
import fr.unice.polytech.hcs.flows.refund.RefundArchiver;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;
import static org.apache.camel.builder.Builder.constant;

public class ExplanationProviderTest extends ActiveMQTest {


    private MongoClient mockClient;
    private DB mockDB;
    private Fongo fongo;
    private Travel borabora;
    private DBCollection dbCollection;

    private final RouteBuilder routeBuilder;
    private String idMongo;
    private String targetSave;
    private String targetGet;

    public ExplanationProviderTest(){
        System.out.println("create !");
        targetGet = GET_TRAVEL_DB_OBJECT;
        targetSave = Endpoints.SAVE_TRAVEL_DATABASE_EP;
        routeBuilder = new ExplanationProvider();
    }


    @Override
    protected JndiRegistry createRegistry() throws Exception {
        System.out.println("createRegistry start ! ");

        fongo = new Fongo("database");

        mockDB = fongo.getDB("expense");

        mockClient = PowerMockito.mock(MongoClient.class);

        PowerMockito.when(mockClient.getDB(Mockito.anyString()))
                .thenReturn(mockDB);

        PowerMockito.whenNew(MongoClient.class).withAnyArguments().thenReturn(mockClient);

        JndiRegistry jndi = super.createRegistry();

        jndi.bind("myDb", mockClient);

        Expense expense = new Expense();
        expense.price = 10;
        expense.evidence = "ici.jpg";
        expense.category = "buisiness";
        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense);

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("travelId", "123");
        basicDBObject.append("status", Status.ONGOING);
        basicDBObject.append("documents", expenses);

        mockDB.createCollection("expenses", basicDBObject);
        mockDB.getCollection("expenses").insert(basicDBObject);
        DBObject objectMongo = mockDB.getCollection("expenses").find(basicDBObject).one();

        idMongo = (String) objectMongo.get("_id");
        return jndi;
    }


    public void initVariables() throws Exception {
        this.borabora = new Travel();
        borabora.status = Status.ONGOING;
        borabora.travelId = "123";
        Expense expense = new Expense();
        expense.price = 10;
        expense.evidence = "ici.jpg";
        expense.category = "buisiness";

        borabora.documents = Collections.singletonList(expense);

    }


        @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new ExplanationProvider();
    }

    @Before
    public void init() throws Exception {
        initVariables();
        resetMocks();
    }


    @Test
    public void TestExplanationProviderChecker() throws InterruptedException {
        assertNotNull(context.hasEndpoint(EXPLANATION_PROVIDER));
        assertNotNull(context.hasEndpoint(targetGet));
        assertNotNull(context.hasEndpoint(targetSave));

        JSONObject jsonObject = new JSONObject();

    }

}