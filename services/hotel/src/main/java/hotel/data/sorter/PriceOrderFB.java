package hotel.data.sorter;

import hotel.data.HotelQueryBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Sorts.*;

public class PriceOrderFB implements SorterBuilder<Bson> {
    static {
        HotelQueryBuilder.addSorter(new PriceOrderFB());
    }

    @Override
    public Bson buildSorter(Document bson) {
        if (!bson.containsKey("order")) {
            return null;
        }

        return bson.getString("order").equalsIgnoreCase("ascending")
                        ? ascending("night_price")
                        : descending("night_price");
    }
}
