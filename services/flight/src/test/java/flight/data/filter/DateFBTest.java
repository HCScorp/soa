package flight.data.filter;


import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.mongodb.client.model.Filters.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DateFBTest {
    @Test
    void dateFilterTest() {
        Bson expected = eq("date", LocalDate.of(2017, 12, 21).toString());

        Document input = new Document();

        input.put("date", LocalDate.of(2017, 12, 21).toString());

        DateFB dateFB = new DateFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                dateFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void dateFilterNullTest() {
        Document input = new Document();
        input.put("notDate", LocalDate.of(2017, 2, 10).toString());

        DateFB dateFB = new DateFB();

        assertNull(dateFB.buildFilter(input));
    }
}
