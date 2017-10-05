package hotel.data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

public class HotelQueryHandler {

    private final Query query;

    public HotelQueryHandler(Query query) {
        this.query = query;
    }

    public List<Document> performOn(MongoCollection<Document> collection) {
        List<Document> result = new LinkedList<>();
        collection.find(query.filter).sort(query.sorter).iterator().forEachRemaining(result::add);
        return result;
    }
}
