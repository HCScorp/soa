package flight.data.sorter;

import com.mongodb.MongoClient;
import flight.data.exception.IllegalSorterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Sorts.ascending;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceOrderFBTest {

    @Test
    void priceOrderSorterTest() {
        Bson expected = ascending("price");

        Document input = new Document();
        input.put("order", "ASCENDING");

        PriceOrderFB priceOrderFB = new PriceOrderFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                priceOrderFB.buildSorter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void priceOrderSorterNullTest() {
        Bson expected = ascending("price");
        
        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                new PriceOrderFB().buildSorter(new Document()).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void priceOrderSorterExceptionTest() {
        Document input = new Document();
        input.put("order", "gejioewgjoiwge");

        PriceOrderFB priceOrderFB = new PriceOrderFB();

        assertThrows(IllegalSorterValueException.class, () -> priceOrderFB.buildSorter(input));
    }
}
