package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import com.mongodb.*;
import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Status;
import fr.unice.polytech.hcs.flows.expense.Travel;
import fr.unice.polytech.hcs.flows.refund.RefundArchiver;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

//



import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ExplanationProviderTest extends ActiveMQTest {


    private MongoClient mockClient;
    private DB mockDB;
    private Fongo fongo;
    private BasicDBObject borabora;
    private DBCollection dbCollection;

    private final RouteBuilder routeBuilder;
    private String idMongo;
    private String targetSave;
    private String targetGet;
    private String mockEndpoint;

    public ExplanationProviderTest() {

        mockEndpoint = "mock://" + GET_TRAVEL_DB_OBJECT;

        targetSave = SAVE_TRAVEL_DATABASE_EP;

        routeBuilder = new ExplanationProvider();
    }

    // Create the registry in order to mock the mongoDB database.

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        fongo = new Fongo("database");
        mockDB = fongo.getDB("expense");


        mockClient = PowerMockito.mock(MongoClient.class);
        PowerMockito.when(mockClient.getDB(Mockito.anyString()))
                .thenReturn(mockDB);
        // Mock the call of the mongoDB databse.
        PowerMockito.whenNew(MongoClient.class).withAnyArguments().thenReturn(mockClient);
        JndiRegistry jndi = super.createRegistry();

        // myDb is the adresse of our mongodb database in the system.
        jndi.bind("myDb", mockClient);

        HashMap<String, String> ex = new HashMap<>();
        ex.put("price", "blabla");
        ex.put("evidence", "bottle");
        ex.put("category", "lol");

        BasicDBObject basicDBObject = new BasicDBObject();

        basicDBObject.append("travelId", "123");
        basicDBObject.append("status", Status.WAITING);
        basicDBObject.append("documents", Collections.singletonList(ex));

        borabora = basicDBObject;
        // create the collection in order to feed it.
        mockDB.createCollection("expenses", new BasicDBObject());
        mockDB.getCollection("expenses").insert(basicDBObject);

        // Catch th Mongo ID.
        DBObject objectMongo = mockDB.getCollection("expenses").find(basicDBObject).one();
        idMongo = objectMongo.get("_id").toString();


        return jndi;
    }


    @Before
    public void initTravel(){

    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return routeBuilder;
    }


    @Override
    public String isMockEndpointsAndSkip() {
        return GET_TRAVEL_DB_OBJECT;
    }


    @Before
    public void initMocks() {
        resetMocks();

        getMockEndpoint(mockEndpoint).whenAnyExchangeReceived((Exchange exc) -> {
            exc.getIn().setBody(borabora);
        });
    }


    @Test
    public void TestExplanationProviderChecker() throws InterruptedException, JSONException {
        // Testing on the camel context
        assertNotNull(context.hasEndpoint(targetSave));
        assertNotNull(context.hasEndpoint(mockEndpoint));
        isAvailableAndMocked(GET_TRAVEL_DB_OBJECT);
        // First condition : send the message to the database.
        getMockEndpoint(mockEndpoint).expectedMessageCount(1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", idMongo);
        jsonObject.put("explanation", "I Love Camel, only for smocking. ");

        // verify
        mock(GET_TRAVEL_DB_OBJECT).expectedMessageCount(1);

        template.requestBody(EXPLANATION_PROVIDER, jsonObject.toString());
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
        // Verify that the object has been updated in the Db.
        DBObject basic = mockDB.getCollection("expenses").findOne();
        assertEquals(basic.get("explanation"), jsonObject.get("explanation"));

    }


    @Test
    public void TestExplanationAnswerAcceptTest() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("travelId", idMongo);
        jsonObject.put("acceptRefund", true);
        template.requestBody(EXPLANATION_ANSWER, jsonObject.toString());

        DBObject basic = mockDB.getCollection("expenses").findOne();
        assertEquals(basic.get("status"), Status.REFUND_ACCEPTED);
    }

    @Test
    public void TestExplanationAnswerRefused() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("travelId", idMongo);
        jsonObject.put("acceptRefund", false);
        template.requestBody(EXPLANATION_ANSWER, jsonObject.toString());

        DBObject basic = mockDB.getCollection("expenses").findOne();
        assertEquals(basic.get("status"), Status.REFUND_REFUSED);
    }


}