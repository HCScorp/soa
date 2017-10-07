package car.data.filter;


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

class DurationFBTest {
    @Test
    void dateFilterTest() {
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2017, 12, 21),
                LocalDate.of(2017, 12, 22),
                LocalDate.of(2017, 12, 23)
        );

        Bson expected = not(elemMatch("bookedDays",
                in("date", dates.stream()
                        .map(LocalDate::toString)
                        .collect(Collectors.toList()))));

        Document input = new Document();

        input.put("dateFrom", LocalDate.of(2017, 12, 21).toString());
        input.put("dateTo", LocalDate.of(2017, 12, 23).toString());

        DurationFB durationFB = new DurationFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                durationFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void dateFilterNullTest() {
        Document input = new Document();
        input.put("dateTo", LocalDate.of(2017, 2, 10).toString());

        DurationFB durationFB = new DurationFB();

        assertNull(durationFB.buildFilter(input));
    }
}
