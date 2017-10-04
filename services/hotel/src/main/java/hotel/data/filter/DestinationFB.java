package hotel.data.filter;

import hotel.data.HotelQueryBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;

public class DestinationFB implements FilterBuilder<Bson> {
    static {
        HotelQueryBuilder.addFilter(new DestinationFB());
    }

    @Override
    public Bson buildFilter(Document bson) {
        return eq("city", bson.getString("destination"));
    }
}
