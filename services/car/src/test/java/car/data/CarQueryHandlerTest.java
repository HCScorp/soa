package car.data;

import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import car.service.Car;
import org.bson.Document;
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

public class CarQueryHandlerTest {

    private Fongo client;
    private MongoDatabase db;
    private MongoCollection<Document> cars;

    @Test
    void queryHandlerTest() {
        client = new Fongo("mongo server handler test");
        db = client.getDatabase("car");
        db.drop();
        cars = db.getCollection("cars");

        Car tesla = new Car("Tesla", "Nice", "S", "888-444", Collections.singletonList(LocalDate.of(2017, 12, 24)));
        cars.insertOne(tesla.toBson());

        List<LocalDate> bookedDays = Arrays.asList(
                LocalDate.of(2017, 7, 14), LocalDate.of(2017, 8, 15)
        );
        Car mercedes = new Car("Mercedes", "Nice", "Class A", "777-666", bookedDays);
        cars.insertOne(mercedes.toBson());

        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2017, 12, 22),
                LocalDate.of(2017, 12, 23),
                LocalDate.of(2017, 12, 24)
        );

        Query query = new Query(
                not(elemMatch("bookedDays",
                        in("date", dates.stream()
                                .map(LocalDate::toString)
                                .collect(Collectors.toList())))),
                null);

        CarQueryHandler queryHandler = new CarQueryHandler(query);
        List<Document> result = queryHandler.performOn(cars);

        assertEquals(1, result.size());
        assertEquals(mercedes, new Car(result.get(0)));
    }
}
