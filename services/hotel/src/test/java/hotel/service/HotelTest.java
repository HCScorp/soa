package hotel.service;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelTest {

    private Document negrescoBson;
    private Hotel negresco;

    @BeforeEach
    void beforeAll() {
        negrescoBson = new Document();
        negrescoBson.put("name", "Negresco");
        negrescoBson.put("night_price", 300);
        Calendar cal = Calendar.getInstance();
        cal.set(2017, Calendar.DECEMBER, 24);
        negrescoBson.put("full_booked_days", Collections.singletonList(cal.getTime()));
        negrescoBson.put("city", "Nice");
        negrescoBson.put("zip_code", "06000");
        negrescoBson.put("address", "37, promenade des Anglais");

        negresco = new Hotel();
        negresco.name = "Negresco";
        negresco.nightPrice = 300;
        negresco.fullBookedDays = Collections.singletonList(cal.getTime());
        negresco.city =  "Nice";
        negresco.zipCode =  "06000";
        negresco.address =  "37, promenade des Anglais";
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
