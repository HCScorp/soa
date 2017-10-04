package hotel.data.sorter;

import hotel.data.sorter.OrderFB;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderFBTest {

    @Test
    void orderTest() {
        Bson expected = ascending("night_price");

        Document input = new Document();
        input.put("order", "ascending");

        OrderFB orderFB = new OrderFB();

        assertEquals(
                expected.toString(),
                orderFB.buildSorter(input).toString());
    }
}
