package hotel.data.filter;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DestinationFBTest {

    @Test
    void destinationTest() {
        Bson expected = eq("city", "Nice");


        Document input = new Document();
        input.put("destination", "Nice");

        DestinationFB destFB = new DestinationFB();

        assertEquals(
                expected.toString(),
                destFB.buildFilter(input).toString());
    }
}
