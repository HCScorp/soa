package paf;


import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONArray;
import org.json.JSONObject;

// take flight from data base and process a filter on the result given.
public class Handler {


    static JSONObject filterByDate(JSONObject input) {
        MongoCollection flights = getFilghts();
        String filter = input.getString("date");
        MongoCursor<Flight> cursor =
                flights.find("{flight: {$regex: #}}", filter ).as(Flight.class);
        if (cursor == null){
            throw new RuntimeException("no flight found for these date");
        }
        JSONArray contents = new JSONArray(); int size = 0;
        while(cursor.hasNext()) {
            contents.put(cursor.next().toJson()); size++;
        }
        return new JSONObject().put("size", size).put("flights", contents);
    }

    static JSONObject filterByCategory(JSONObject input) {
        return null;
    }

    static JSONObject filterByDestination(JSONObject input) {
        return null;
    }

    static JSONObject filterBy(JSONObject input) {
        return null;
    }



    private static MongoCollection getFilghts() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}
