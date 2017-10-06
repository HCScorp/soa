package flight.service;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightTest {

    private Document parisNiceBson;
    private Flight parisNice;

    @BeforeEach
    void beforeAll() {
        parisNiceBson = new Document();
        parisNiceBson.put("origin", "Paris");
        parisNiceBson.put("destination", "Nice");
        parisNiceBson.put("date", LocalDate.of(2017, 12, 24).toString());
        parisNiceBson.put("price", 50);
        parisNiceBson.put("journeyType", Flight.JourneyType.DIRECT.toString());
        parisNiceBson.put("duration", Duration.ofHours(1).toMinutes());
        parisNiceBson.put("category", Flight.Category.ECO.toString());
        parisNiceBson.put("airline", "Air France");

        parisNice = new Flight(
                "Paris", "Nice",
                LocalDate.of(2017, 12, 24), 50,
                Flight.JourneyType.DIRECT, Duration.ofHours(1),
                Flight.Category.ECO, "Air France");
    }

    @Test
    void bsonToFlightTest() {
        assertEquals(parisNice, new Flight(parisNiceBson));
    }

    @Test
    void flightToBsonTest() {
        assertEquals(parisNiceBson, parisNice.toBson());
    }
}
