package flight.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DB {
    public static MongoCollection<Document> getFlights() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return client.getDatabase(Network.DATABASE).getCollection(Network.COLLECTION);
    }
}