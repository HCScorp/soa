package hotel.data.filter;

import hotel.data.HotelQueryBuilder;
import hotel.util.DateRange;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;

public class DateFB implements FilterBuilder<Bson> {
    static {
        HotelQueryBuilder.addFilter(new DateFB());
    }

    @Override
    public Bson buildFilter(Document bson) {
        if (!bson.containsKey("date_from") || !bson.containsKey("date_to")) {
            return null;
        }

        LocalDate fromDate = LocalDate.parse(bson.getString("date_from"));
        LocalDate toDate = LocalDate.parse(bson.getString("date_to"));

        DateRange stayDates = new DateRange(fromDate, toDate).exclusive();

        return not(elemMatch("full_booked_days",
                in("date", stayDates.stream()
                        .map(LocalDate::toString)
                        .collect(Collectors.toList()))));
    }
}
