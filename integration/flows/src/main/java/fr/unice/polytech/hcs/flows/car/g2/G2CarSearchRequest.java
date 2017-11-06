package fr.unice.polytech.hcs.flows.car.g2;

import fr.unice.polytech.hcs.flows.car.CarSearchRequest;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;

import static java.time.temporal.ChronoUnit.DAYS;

public class G2CarSearchRequest implements Serializable {

    public String destination;
    public long date;
    public long duration; // in days

    G2CarSearchRequest(CarSearchRequest csr) {
        this.destination = csr.city;
        LocalDate dateFrom = LocalDate.parse(csr.dateFrom);
        LocalDate dateTo = LocalDate.parse(csr.dateTo);
        this.date = dateFrom.atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
        this.duration = DAYS.between(dateFrom, dateTo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        G2CarSearchRequest that = (G2CarSearchRequest) o;

        if (date != that.date) return false;
        if (duration != that.duration) return false;
        return destination != null ? destination.equals(that.destination) : that.destination == null;
    }

    @Override
    public int hashCode() {
        int result = destination != null ? destination.hashCode() : 0;
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        return result;
    }
}
