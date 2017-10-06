package flight.data.filter;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OriginFBTest {

    @Test
    void originFilterTest() {
        Bson expected = eq("origin", "Nice");

        Document input = new Document();
        input.put("origin", "Nice");

        OriginFB originFB = new OriginFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                originFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void originFilterNullTest() {
        Document input = new Document();
        input.put("notDestination", "Nice");

        OriginFB originFB = new OriginFB();

        assertNull(originFB.buildFilter(input));
    }
}
