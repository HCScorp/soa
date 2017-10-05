package hotel.data.filter;


import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DateFBTest {
    @Test
    void dateFilterTest() {
        List<LocalDate> stayDates = Arrays.asList(
                LocalDate.of(2017, 12, 21),
                LocalDate.of(2017, 12, 22),
                LocalDate.of(2017, 12, 23)
        );

        Bson expected = not(elemMatch("full_booked_days",
                in("date", stayDates.stream()
                        .map(LocalDate::toString)
                        .collect(Collectors.toList()))));

        Document input = new Document();

        input.put("date_from", LocalDate.of(2017, 12, 21).toString());
        input.put("date_to", LocalDate.of(2017, 12, 24).toString());

        DateFB dateFB = new DateFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                dateFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void dateFilterNullTest() {
        Document input = new Document();
        input.put("date_to", LocalDate.of(2017, 2, 10).toString());

        DateFB dateFB = new DateFB();

        assertNull(dateFB.buildFilter(input));
    }
}
