package flight.data.filter;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AirlineFBTest {

    @Test
    void airlineFilterTest() {
        Bson expected = eq("airline", "Air France");

        Document input = new Document();
        input.put("airline", "Air France");

        AirlineFB airlineFB = new AirlineFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                airlineFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void airlineFilterNullTest() {
        Document input = new Document();
        input.put("notAirline", "Air France");

        AirlineFB airlineFB = new AirlineFB();

        assertNull(airlineFB.buildFilter(input));
    }
}
