package flight.data.filter;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class AirlineFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("airline")) {
            return null;
        }

        return eq("airline", bson.getString("airline"));
    }
}
