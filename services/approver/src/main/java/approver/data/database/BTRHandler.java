package approver.data.database;

import approver.data.BusinessTravelRequest;
import approver.data.database.exception.BTRNotFound;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class BTRHandler {
    public static BusinessTravelRequest get(int id) throws BTRNotFound {
        Bson filter = eq("id", id);

        Document result = DB.getBTR().find(filter).first();

        if(result == null || result.isEmpty()){
            throw new BTRNotFound(id);
        }

        return new BusinessTravelRequest(result);
    }

    public static void insert(Document document){
        DB.getBTR().insertOne(document);
    }

    public static void update(int id, String field, String value) throws BTRNotFound {
        Document btr = get(id).toBSON();
        btr.put(field, value);

        DB.getBTR().updateOne(get(id).toBSON(), btr);
    }
}
