package car.data.filter;

import car.data.exception.IllegalFilterValueException;
import car.util.DateRange;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;

public class PlaceFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) throws IllegalFilterValueException {
        if (!bson.containsKey("city")) {
            return null;
        }

        return eq("city", bson.getString("city"));
    }
}
