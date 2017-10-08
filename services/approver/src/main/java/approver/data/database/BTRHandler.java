package approver.data.database;

import approver.data.BusinessTravelRequest;
import approver.data.database.exception.BTRNotFound;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class BTRHandler {

    private final MongoCollection<Document> btrCollection;

    public BTRHandler() {
        this.btrCollection = DB.getBTR();
    }

    public BTRHandler(MongoCollection<Document> btrCollection) {
        this.btrCollection = btrCollection;
    }

    private Document getDocument(ObjectId id) throws BTRNotFound {
        Document result = btrCollection.find(eq("_id", id)).first();

        if (result == null) {
            throw new BTRNotFound(id);
        }

        return result;
    }

    public BusinessTravelRequest getBTR(ObjectId id) throws BTRNotFound {
        return new BusinessTravelRequest(getDocument(id));
    }

    public ObjectId insert(BusinessTravelRequest btr) {
        Document bson = btr.toBSON();
        btrCollection.insertOne(bson);
        ObjectId id = bson.getObjectId("_id");
        btr.setId(id);
        return id;
    }

    public void update(ObjectId id, String field, Object value) throws BTRNotFound {
        btrCollection.updateOne(eq("_id", id), new Document("$set", new Document(field, value)));
    }
}
