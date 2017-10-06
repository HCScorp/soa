package flight.data.filter;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class DestinationFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("destination")) {
            return null;
        }

        return eq("destination", bson.getString("destination"));
    }
}
