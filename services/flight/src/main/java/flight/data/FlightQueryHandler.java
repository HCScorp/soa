package flight.data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

public class FlightQueryHandler {

    private final Query query;

    public FlightQueryHandler(Query query) {
        this.query = query;
    }

    public List<Document> performOn(MongoCollection<Document> collection) {
        List<Document> result = new LinkedList<>();
        collection.find(query.filter)
                .sort(query.sorter)
                .iterator()
                .forEachRemaining(result::add);
        return result;
    }
}
