package hotel.data.filter;

import hotel.data.exception.IllegalFilterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;

public class DestinationFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) throws IllegalFilterValueException {
        if (!bson.containsKey("city")) {
            return null;
        }

        return eq("city", bson.getString("city"));
    }
}
