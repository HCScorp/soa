package hotel.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import hotel.data.Network;
import org.bson.Document;


public class DB {
    public  static MongoCollection<Document> getHotels() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return client.getDatabase(Network.DATABASE).getCollection(Network.COLLECTION);
    }
}