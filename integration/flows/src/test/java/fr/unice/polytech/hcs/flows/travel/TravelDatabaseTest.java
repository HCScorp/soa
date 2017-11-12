package fr.unice.polytech.hcs.flows.travel;

import com.github.fakemongo.Fongo;
import com.mongodb.*;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Status;
import fr.unice.polytech.hcs.flows.expense.Travel;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.SAVE_TRAVEL_DATABASE_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.UPDATE_TRAVEL;

public class TravelDatabaseTest extends CamelTestSupport {


    private MongoClient mockClient;
    private DB mockDB;
    private Fongo fongo;


    private BasicDBObject borabora;


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

        return jndi;
    }
    private Travel vietnam;
    @Before
    public void initVariable(){
        vietnam =  new Travel();
        vietnam.status = Status.WAITING;
        vietnam.travelId = "123";
        vietnam.explanation = "taux alcoolimie = 3gr";

        Expense ex = new Expense();
        ex.price = 12.0;
        ex.evidence = "Bia Tiger";
        ex.category = "alcool";

        vietnam.documents = Collections.singletonList(ex);
    }

    @Override
    public RouteBuilder createRouteBuilder() throws Exception{
        return new TravelDatabase();
    }


    @Test
    public void TestTravelDatabaseUpdate() throws InterruptedException, JSONException {
        assertNotNull(context.hasEndpoint(UPDATE_TRAVEL));
        assertNotNull(mockDB.getCollection("expenses").findOne());

        template.requestBody(UPDATE_TRAVEL, vietnam);
        DBObject dbObject = mockDB.getCollection("expenses").findOne();
        System.out.println(" new dbObject == " + dbObject);
    }



}
