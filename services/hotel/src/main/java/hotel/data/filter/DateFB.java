package hotel.data.filter;

import hotel.data.HotelQueryBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.nin;

public class DateFB implements FilterBuilder<Bson> {
    static {
        HotelQueryBuilder.addFilter(new DateFB());
    }

    @Override
    public Bson buildFilter(Document bson) {
        Date fromDate = bson.getDate("date_from");
        Date toDate = bson.getDate("date_to");

        List<Date> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        while (cal.getTime().before(toDate)) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }

        return nin("fullBookedDays", dates);
    }
}
