package flight.data.filter;

import flight.data.exception.IllegalFilterValueException;
import flight.service.Flight;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class JourneyTypeFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("journeyType")) {
            return null;
        }

        try {
            Flight.JourneyType type = Flight.JourneyType.valueOf(bson.getString("journeyType"));
            return eq("journeyType", type.toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalFilterValueException("incorrect 'journeyType' value");
        }
    }
}
