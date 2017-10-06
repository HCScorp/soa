package flight.data.filter;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class OriginFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("origin")) {
            return null;
        }

        return eq("origin", bson.getString("origin"));
    }
}
