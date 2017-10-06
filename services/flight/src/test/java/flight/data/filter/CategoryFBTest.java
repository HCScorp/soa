package flight.data.filter;

import com.mongodb.MongoClient;
import flight.data.exception.IllegalFilterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

class CategoryFBTest {

    @Test
    void categoryFilterTest() {
        Bson expected = eq("category", "ECO");

        Document input = new Document();
        input.put("category", "ECO");

        CategoryFB categoryFB = new CategoryFB();

        assertEquals(
                expected.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()),
                categoryFB.buildFilter(input).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()));
    }

    @Test
    void categoryFilterNullTest() {
        Document input = new Document();
        input.put("notCategory", "ECO");

        CategoryFB categoryFB = new CategoryFB();

        assertNull(categoryFB.buildFilter(input));
    }

    @Test
    void categoryFilterExceptionTest() {
        Document input = new Document();
        input.put("category", "FEKOQGQEJIG");

        CategoryFB categoryFB = new CategoryFB();

        assertThrows(IllegalFilterValueException.class, () -> categoryFB.buildFilter(input));
    }
}
