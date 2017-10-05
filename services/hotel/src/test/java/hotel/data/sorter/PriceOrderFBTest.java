package hotel.data.sorter;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PriceOrderFBTest {

    @Test
    void priceOrderSorterTest() {
        Bson expected = ascending("night_price");

        Document input = new Document();
        input.put("order", "ascending");

        PriceOrderFB priceOrderFB = new PriceOrderFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                priceOrderFB.buildSorter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void priceOrderSorterNullTest() {
        Document input = new Document();
        input.put("notOrder", "ascending");

        PriceOrderFB priceOrderFB = new PriceOrderFB();

        assertNull(priceOrderFB.buildSorter(input));
    }
}
