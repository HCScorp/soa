package hotel.service;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelTest {

    private Document negrescoBson;
    private Hotel negresco;

    @BeforeEach
    void beforeAll() {
        negrescoBson = new Document();
        negrescoBson.put("name", "Negresco");
        negrescoBson.put("city", "Nice");
        negrescoBson.put("zipCode", "06000");
        negrescoBson.put("address", "37, promenade des Anglais");
        negrescoBson.put("nightPrice", 300);
        negrescoBson.put("fullBookedDays", Collections.singletonList(new Document("date", LocalDate.of(2017, 12, 24).toString())));

        negresco = new Hotel(
                "Negresco", "Nice", "06000", "37, promenade des Anglais",
                300, Collections.singletonList(LocalDate.of(2017, 12, 24)));
    }

    @Test
    void bsonToHotelTest() {
        assertEquals(negresco, new Hotel(negrescoBson));
    }

    @Test
    void hotelToBsonTest() {
        assertEquals(negrescoBson, negresco.toBson());
    }
}
