package fr.unice.polytech.hcs.flows.explanation;

import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

//

public class ExplanationProviderTest extends ActiveMQTest {


    private final RouteBuilder routeBuilder;
    private String mockEndpoint;

    private Travel borabora;

    public ExplanationProviderTest() {

        mockEndpoint = "mock://" + GET_TRAVEL;
        routeBuilder = new ExplanationProvider();
    }

    // Create the registry in order to mock the mongoDB database.




    @Before
    public void initTravel(){
        borabora = new Travel();
        borabora.travelId = "123";
        borabora.status = "Done";
        Expense ex = new Expense();
        ex.category = "trololo";
        ex.evidence = "bouteille_de_vin.jpg";
        ex.price = 1200.5;
        borabora.documents = Collections.singletonList(ex);
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return routeBuilder;
    }


    @Override
    public String isMockEndpointsAndSkip() {

        return GET_TRAVEL + "|" + ACCEPT_REFUND + "|" + REFUSE_REFUND;
    }


    @Before
    public void initMocks() {
        resetMocks();

        getMockEndpoint(mockEndpoint).whenAnyExchangeReceived((Exchange exc) -> {
            exc.getIn().setBody(borabora);
        });
    }

//
//    @Test
//    public void TestExplanationProviderChecker() throws InterruptedException, JSONException {
//        // Testing on the camel context
//        assertNotNull(context.hasEndpoint(targetSave));
//        assertNotNull(context.hasEndpoint(mockEndpoint));
//        isAvailableAndMocked(GET_TRAVEL_DB_OBJECT);
//        // First condition : send the message to the database.
//        getMockEndpoint(mockEndpoint).expectedMessageCount(1);
//
//        JSONObject jsonObject = new JSONObject();
//        // jsonObject.put("id", idMongo);
//        jsonObject.put("explanation", "I Love Camel, only for smocking. ");
//
//        // verify
//        mock(GET_TRAVEL_DB_OBJECT).expectedMessageCount(1);
//
//        template.requestBody(EXPLANATION_PROVIDER, jsonObject.toString());
//        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
//        // Verify that the object has been updated in the Db.
//        DBObject basic = mockDB.getCollection("expenses").findOne();
//        assertEquals(basic.get("explanation"), jsonObject.get("explanation"));
//
//    }



    @Test
    public void TestExplanationAnswerAcceptTest() throws JSONException, InterruptedException {
        assertNotNull(context.hasEndpoint(EXPLANATION_ANSWER));
        assertNotNull(context.hasEndpoint(ACCEPT_REFUND));
        assertNotNull(context.hasEndpoint(REFUSE_REFUND));

        mock(GET_TRAVEL).expectedMessageCount(1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("travelId", "123");
        jsonObject.put("acceptRefund", true);
        template.requestBody(EXPLANATION_ANSWER, jsonObject.toString());
        assertMockEndpointsSatisfied();

    }

    @Test
    public void TestExplanationAnswerRefused() throws JSONException {

//        JSONObject jsonObject = new JSONObject();
//        // jsonObject.put("travelId", idMongo);
//        jsonObject.put("acceptRefund", false);
//        template.requestBody(EXPLANATION_ANSWER, jsonObject.toString());
//
//        DBObject basic = mockDB.getCollection("expenses").findOne();
//        assertEquals(basic.get("status"), Status.REFUND_REFUSED);
    }


}