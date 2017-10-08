package approver.data.database;

import approver.data.BusinessTravelRequest;
import approver.data.database.exception.BTRNotFound;
import com.mongodb.bulk.UpdateRequest;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class BTRHandler {
    public static DB db = null;

    private static void checkConnection(){
        if(db == null){
            db = new DB();
        }
    }
    public static BusinessTravelRequest get(int id) throws BTRNotFound {
        checkConnection();
        Bson filter = eq("id", id);

        Document result = db.getBTR().find(filter).first();

        if(result == null || result.isEmpty()){
            throw new BTRNotFound(id);
        }

        return new BusinessTravelRequest(result);
    }

    public static void insert(Document document){
        checkConnection();
        db.getBTR().insertOne(document);
    }

    public static void update(int id, String field, String value) throws BTRNotFound {
        checkConnection();
        Document btr = get(id).toBSON();
        btr.put(field, value);

        UpdateResult result = db.getBTR().replaceOne(get(id).toBSON(), btr);
    }
}
