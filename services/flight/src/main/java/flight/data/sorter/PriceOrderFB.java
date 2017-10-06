package flight.data.sorter;

import flight.data.exception.IllegalSorterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

public class PriceOrderFB implements SorterBuilder<Bson> {
    @Override
    public Bson buildSorter(Document bson) {
        if (!bson.containsKey("order")) {
            return null;
        }

        String order = bson.getString("order");
        if (order.equalsIgnoreCase("ascending")) {
            return ascending("price");
        } else if (order.equalsIgnoreCase("descending")) {
            return descending("price");
        } else {
            throw new IllegalSorterValueException("incorrect 'order' value");
        }
    }
}
