package flight.data.filter;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lte;

public class MaxTravelTimeFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("maxTravelTime")) { // in minutes
            return null;
        }

        return lte("duration", bson.getInteger("maxTravelTime").longValue());
    }
}
