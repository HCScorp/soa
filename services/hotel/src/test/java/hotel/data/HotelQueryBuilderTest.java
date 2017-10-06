package hotel.data;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelQueryBuilderTest {

    @Test
    void queryBuilderTest() {
        List<LocalDate> stayDates = Arrays.asList(
                LocalDate.of(2017, 12, 21),
                LocalDate.of(2017, 12, 22),
                LocalDate.of(2017, 12, 23)
        );

        Bson expectedFilter = and(not(elemMatch("fullBookedDays",
                in("date", stayDates.stream()
                        .map(LocalDate::toString)
                        .collect(Collectors.toList())))), eq("city", "Nice"));
        Bson expectedSorter = orderBy(ascending("nightPrice"));

        Document input = new Document();
        input.put("order", "ascending");
        input.put("destination", "Nice");
        input.put("dateFrom", LocalDate.of(2017, 12, 21).toString());
        input.put("dateTo", LocalDate.of(2017, 12, 24).toString());

        Query query = HotelQueryBuilder.buildQuery(input);

        assertEquals(expectedFilter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                query.filter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry())
        );

        assertEquals(expectedSorter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                query.sorter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }
}
