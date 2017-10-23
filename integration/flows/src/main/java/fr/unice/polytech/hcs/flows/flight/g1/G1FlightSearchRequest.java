package fr.unice.polytech.hcs.flows.flight.g1;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class G1FlightSearchRequest implements Serializable {

    @JsonProperty private String event;
    @JsonProperty private String destination;
    @JsonProperty private String departure;
    @JsonProperty private long departureTimeStamp;

    public G1FlightSearchRequest(FlightSearchRequest fsr) {
        this.event = "list";
        this.destination = fsr.destination;
        this.departure = fsr.origin;

        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.parse(fsr.date),
                LocalTime.parse(fsr.timeFrom));
        this.departureTimeStamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        G1FlightSearchRequest that = (G1FlightSearchRequest) o;

        if (departureTimeStamp != that.departureTimeStamp) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        return departure != null ? departure.equals(that.departure) : that.departure == null;
    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (int) (departureTimeStamp ^ (departureTimeStamp >>> 32));
        return result;
    }
    // TODO
}
