package flight.data;

import com.mongodb.MongoClient;
import flight.service.Flight;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightQueryBuilderTest {

    @Test
    void queryBuilderTest() {
        Bson expectedFilter = and(
                eq("origin", "Paris"),
                eq("destination", "Nice"),
                eq("date", LocalDate.of(2017, 12, 21).toString()),
                eq("journeyType", Flight.JourneyType.DIRECT.toString()),
                lte("duration", Duration.ofHours(2).toMinutes()),
                eq("category", Flight.Category.ECO.toString()),
                eq("airline", "Air France"));
        Bson expectedSorter = orderBy(ascending("price"));

        Document input = new Document();
        input.put("origin", "Paris");
        input.put("destination", "Nice");
        input.put("date", LocalDate.of(2017, 12, 21).toString());
        input.put("journeyType", Flight.JourneyType.DIRECT.toString());
        input.put("maxTravelTime", Duration.ofHours(2).toMinutes());
        input.put("category", Flight.Category.ECO.toString());
        input.put("airline", "Air France");
        input.put("order", "ASCENDING");

        Query query = FlightQueryBuilder.buildQuery(input);

        assertEquals(expectedFilter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                query.filter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry())
        );

        assertEquals(expectedSorter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                query.sorter.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }
}
