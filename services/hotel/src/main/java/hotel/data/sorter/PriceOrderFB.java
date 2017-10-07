package hotel.data.sorter;

import hotel.data.exception.IllegalSorterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Sorts.*;

public class PriceOrderFB implements SorterBuilder<Bson> {
    @Override
    public Bson buildSorter(Document bson) throws IllegalSorterValueException {
        if (!bson.containsKey("order")) {
            return ascending("nightPrice");
        }

        String order = bson.getString("order");
        if (order.equalsIgnoreCase("ASCENDING")) {
            return ascending("nightPrice");
        } else if (order.equalsIgnoreCase("DESCENDING")) {
            return descending("nightPrice");
        } else {
            throw new IllegalSorterValueException("incorrect 'order' value");
        }
    }
}
