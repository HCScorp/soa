package fr.unice.polytech.hcs.flows.travel;

import com.github.fakemongo.Fongo;
import com.mongodb.*;
import fr.unice.polytech.hcs.flows.expense.Status;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.Collections;
import java.util.HashMap;

public class TravelDatabaseTest extends CamelTestSupport {


    private MongoClient mockClient;
    private DB mockDB;
    private Fongo fongo;
    private BasicDBObject borabora;
    private DBCollection dbCollection;
    private String idMongo;

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



    @Test
    public void TestExplanationProviderChecker() throws InterruptedException, JSONException {
        // Testing on the camel context


    }
}
