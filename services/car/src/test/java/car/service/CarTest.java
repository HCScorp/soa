package car.service;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {

    private Document teslaBson;
    private Car tesla;

    @BeforeEach
    void beforeAll() {
        teslaBson = new Document();
        teslaBson.put("company", "Tesla");
        teslaBson.put("city", "Nice");
        teslaBson.put("model", "S");
        teslaBson.put("numberPlate", "999-111");
        teslaBson.put("bookedDays", Collections.singletonList(new Document("date", LocalDate.of(2017, 12, 24).toString())));
        tesla = new Car("Tesla", "Nice", "S", "999-111", Collections.singletonList(LocalDate.of(2017, 12, 24)));
    }

    @Test
    void bsonToHotelTest() {
        assertEquals(tesla, new Car(teslaBson));
    }

    @Test
    void hotelToBsonTest() {
        assertEquals(teslaBson, tesla.toBson());
    }
}
