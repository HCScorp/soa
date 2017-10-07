package car.data.filter;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlaceFBTest {

    @Test
    void destinationFilterTest() {
        Bson expected = eq("city", "Nice");

        Document input = new Document();
        input.put("city", "Nice");

        PlaceFB placeFB = new PlaceFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                placeFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void destinationFilterNullTest() {
        Document input = new Document();
        input.put("notCity", "Nice");

        PlaceFB placeFB = new PlaceFB();

        assertNull(placeFB.buildFilter(input));
    }
}
