package flight.data.filter;

import com.mongodb.MongoClient;
import flight.data.exception.IllegalFilterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JourneyTypeFBTest {

    @Test
    void journeyTypeFilterTest() {
        Bson expected = eq("journeyType", "DIRECT");

        Document input = new Document();
        input.put("journeyType", "DIRECT");

        JourneyTypeFB journeyTypeFB = new JourneyTypeFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                journeyTypeFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void journeyTypeFilterNullTest() {
        Document input = new Document();
        input.put("notJourneyType", "DIRECT");

        JourneyTypeFB journeyTypeFB = new JourneyTypeFB();

        assertNull(journeyTypeFB.buildFilter(input));
    }

    @Test
    void journeyTypeFilterExceptionTest() {
        Document input = new Document();
        input.put("journeyType", "FEKOQGQEJIG");

        JourneyTypeFB journeyTypeFB = new JourneyTypeFB();

        assertThrows(IllegalFilterValueException.class, () -> journeyTypeFB.buildFilter(input));
    }
}
