package hotel.data.filter;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DestinationFBTest {

    @Test
    void destinationFilterTest() {
        Bson expected = eq("city", "Nice");

        Document input = new Document();
        input.put("destination", "Nice");

        DestinationFB destFB = new DestinationFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                destFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void destinationFilterNullTest() {
        Document input = new Document();
        input.put("notDestination", "Nice");

        DestinationFB destFB = new DestinationFB();

        assertNull(destFB.buildFilter(input));
    }
}
