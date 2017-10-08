package approver.data.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DB {
    private MongoDatabase db;

    public DB(){
        db = new MongoClient(Network.HOST, Network.PORT).getDatabase(Network.DATABASE);
    }

    public DB(MongoDatabase db){
        this.db = db;
    }

    public MongoCollection<Document> getBTR() {
        return db.getCollection(Network.COLLECTION);
    }
}
