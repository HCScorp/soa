package hotel.data.filter;


import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.nin;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateFBTest {
    @Test
    void filterFormatTest() {
        List<Date> stayDates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(0));

        cal.set(2017, Calendar.DECEMBER, 21);
        stayDates.add(cal.getTime());
        cal.set(2017, Calendar.DECEMBER, 22);
        stayDates.add(cal.getTime());
        cal.set(2017, Calendar.DECEMBER, 23);
        stayDates.add(cal.getTime());

        Bson expected = nin("fullBookedDays", stayDates);


        Calendar calInput = Calendar.getInstance();
        calInput.setTime(new Date(0));
        Document input = new Document();

        calInput.set(2017, Calendar.DECEMBER, 21);
        input.put("date_from", calInput.getTime());

        calInput.set(2017, Calendar.DECEMBER, 24);
        input.put("date_to", calInput.getTime());

        DateFB dateFB = new DateFB();

        assertEquals(
                expected.toString(),
                dateFB.buildFilter(input).toString());
    }
}
