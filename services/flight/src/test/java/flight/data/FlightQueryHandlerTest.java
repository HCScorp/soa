package flight.data;

import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import flight.service.Flight;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightQueryHandlerTest {

    private Fongo client;
    private MongoDatabase db;
    private MongoCollection<Document> hotels;

    @Test
    void queryHandlerTest() {
//        client = new Fongo("mongo server handler test");
//        db = client.getDatabase("hotel");
//        db.drop();
//        hotels = db.getCollection("hotels");
//
//        Hotel negresco = new Flight();
//        hotels.insertOne(negresco.toBson());
//
//        List<LocalDate> fBookedDays = Arrays.asList(
//                LocalDate.of(2017, 7, 14),
//                LocalDate.of(2017, 8, 15)
//        );
//        Hotel meridien = new Flight("Le Méridien", "Nice", "06046", "1, promenade des Anglais", 200, fBookedDays);
//        hotels.insertOne(meridien.toBson());
//
//        List<LocalDate> stayDates = Arrays.asList(
//                LocalDate.of(2017, 12, 22),
//                LocalDate.of(2017, 12, 23),
//                LocalDate.of(2017, 12, 24)
//        );
//
//        Query query = new Query(
//                not(elemMatch("fullBookedDays",
//                        in("date", stayDates.stream()
//                                .map(LocalDate::toString)
//                                .collect(Collectors.toList())))),
//                orderBy(ascending("nightPrice")));
//
//        FlightQueryHandler queryHandler = new FlightQueryHandler(query);
//        List<Document> result = queryHandler.performOn(hotels);
//
//        assertEquals(1, result.size());
//        assertEquals(meridien, new Hotel(result.get(0)));
    }
}
