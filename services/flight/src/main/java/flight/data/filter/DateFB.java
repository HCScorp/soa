package flight.data.filter;

import flight.data.exception.IllegalFilterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.mongodb.client.model.Filters.*;

public class DateFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("date")) {
            return null;
        }

        try {
            LocalDate date = LocalDate.parse(bson.getString("date"));
            return eq("date", date.toString());
        } catch (DateTimeParseException e) {
            throw new IllegalFilterValueException("incorrect 'date' value");
        }
    }
}
