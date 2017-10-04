package hotel.data;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelQueryBuilderTest {

    void test(){
        List<Date> stayDates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(0));
        cal.set(2017, Calendar.DECEMBER, 21);
        stayDates.add(cal.getTime());
        cal.set(2017, Calendar.DECEMBER, 22);
        stayDates.add(cal.getTime());
        cal.set(2017, Calendar.DECEMBER, 23);
        stayDates.add(cal.getTime());

        Bson expectedFilter = and(eq("city", "Nice"), nin("fullBookedDays", stayDates));
        Bson expectedSorter = orderBy(ascending("night_price"));

        Document input = new Document();
        input.put("order", "ascending");
        input.put("destination", "Nice");
        Calendar calInput = Calendar.getInstance();
        calInput.setTime(new Date(0));
        calInput.set(2017, Calendar.DECEMBER, 21);
        input.put("date_from", calInput.getTime());
        calInput.set(2017, Calendar.DECEMBER, 24);
        input.put("date_to", calInput.getTime());

        Query query = HotelQueryBuilder.buildQuery(input);

        assertEquals(query.filter.toString(), expectedFilter.toString());
        assertEquals(query.sorter.toString(), expectedSorter.toString());
    }
}
