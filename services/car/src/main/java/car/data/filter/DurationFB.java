package car.data.filter;

import car.data.exception.IllegalFilterValueException;
import car.util.DateRange;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;

public class DurationFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) throws IllegalFilterValueException {
        if (!bson.containsKey("dateFrom") || !bson.containsKey("dateTo")) {
            return null;
        }

        try {
            LocalDate fromDate = LocalDate.parse(bson.getString("dateFrom"));
            LocalDate toDate = LocalDate.parse(bson.getString("dateTo"));

            DateRange stayDates = new DateRange(fromDate, toDate).inclusive();

            return not(elemMatch("bookedDays",
                    in("date", stayDates.stream()
                            .map(LocalDate::toString)
                            .collect(Collectors.toList()))));
        } catch (DateTimeParseException e) {
            throw new IllegalFilterValueException("incorrect 'dateFrom' or 'dateTo' value");
        }
    }
}
