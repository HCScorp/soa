package hotel.data.filter;

import hotel.data.HotelQueryBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Sorts.*;

public class OrderFB implements SorterBuilder<Bson> {
    static {
        HotelQueryBuilder.addSorter(new OrderFB());
    }

    @Override
    public Bson buildSorter(Document bson) {
        return bson.getString("order").equalsIgnoreCase("ascending")
                        ? ascending("night_price")
                        : descending("night_price");
    }
}
