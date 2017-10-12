package flight.data.filter;


import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.mongodb.client.model.Filters.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TimeFBTest {
    @Test
    void timeFilterTest() {
        Bson expected = and(
                gte("secondOfDay", LocalTime.of(22, 30, 50).toSecondOfDay()),
                lte("secondOfDay", LocalTime.of(23, 30, 50).toSecondOfDay()));

        Document input = new Document();

        input.put("timeFrom", LocalTime.of(22, 30, 50).toString());
        input.put("timeTo", LocalTime.of(23, 30, 50).toString());

        TimeFB timeFB = new TimeFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                timeFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void timeFilterNullTest() {
        Document input = new Document();
        input.put("timeFromage", LocalTime.of(22, 30, 50).toString());
        input.put("timeTomage", LocalTime.of(23, 30, 50).toString());

        TimeFB timeFB = new TimeFB();

        assertNull(timeFB.buildFilter(input));
    }
}
