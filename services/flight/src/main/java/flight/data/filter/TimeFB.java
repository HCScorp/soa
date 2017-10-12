package flight.data.filter;

import flight.data.exception.IllegalFilterValueException;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;

public class TimeFB implements FilterBuilder<Bson> {
    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("timeFrom") && !bson.containsKey("timeTo")) {
            return null;
        }

        try {
            Bson condFrom = null;
            Bson condTo = null;

            if (bson.containsKey("timeFrom")) {
                int fromTime = LocalTime.parse(bson.getString("timeFrom")).toSecondOfDay();
                condFrom = gte("secondOfDay", fromTime);
            }

            if (bson.containsKey("timeTo")) {
                int toTime = LocalTime.parse(bson.getString("timeTo")).toSecondOfDay();
                condTo = lte("secondOfDay", toTime);
            }

            if (condFrom == null) {
                return condTo;
            } else if(condTo == null) {
                return condFrom;
            } else {
                return and(condFrom, condTo);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalFilterValueException("incorrect 'timeFrom' or 'timeTo' value");
        }
    }
}
