package flight.data.filter;

import flight.data.exception.IllegalFilterValueException;
import flight.service.Flight;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class CategoryFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("category")) {
            return null;
        }

        try {
            Flight.Category type = Flight.Category.valueOf(bson.getString("category"));
            return eq("category", type.toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalFilterValueException("incorrect 'category' value");
        }
    }
}
