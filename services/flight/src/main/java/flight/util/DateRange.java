package flight.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class DateRange {

    private final LocalDate start;
    private final LocalDate end;
    private int inclusive = 0;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public DateRange inclusive() {
        this.inclusive = 1;
        return this;
    }

    public DateRange exclusive() {
        this.inclusive = 0;
        return this;
    }

    public Stream<LocalDate> stream() {
        if (start == null || end == null || end.isBefore(start)) {
            return Stream.empty();
        }

        return Stream.iterate(start, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end) + inclusive);
    }
}
