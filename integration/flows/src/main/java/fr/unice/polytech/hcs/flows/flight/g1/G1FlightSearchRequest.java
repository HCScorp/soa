package fr.unice.polytech.hcs.flows.flight.g1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;

import java.io.Serializable;
import java.time.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class G1FlightSearchRequest implements Serializable {

    @JsonProperty private String event;
    @JsonProperty private String destination;
    @JsonProperty private String departure;
    @JsonProperty private String orderBy;
    @JsonProperty private Long departureTimeStamp;
    @JsonProperty private Filter filterBy;

    G1FlightSearchRequest(FlightSearchRequest fsr) {
        this.event = "list";
        this.destination = fsr.destination != null ? fsr.destination : "";
        this.departure = fsr.origin != null ? fsr.origin : "";
        this.orderBy = "price";

        if(fsr.date != null && fsr.timeFrom != null) {
            LocalDateTime dateTime = LocalDateTime.of(
                    LocalDate.parse(fsr.date),
                    LocalTime.parse(fsr.timeFrom));
            this.departureTimeStamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        } else {
            this.departureTimeStamp = Instant.now().getEpochSecond();
        }

        if(fsr.maxTravelTime != null) {
            this.filterBy = new Filter();
            this.filterBy.name = "max_duration";
            this.filterBy.args = new String[]{fsr.maxTravelTime.toString()};
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        G1FlightSearchRequest that = (G1FlightSearchRequest) o;

        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        if (departure != null ? !departure.equals(that.departure) : that.departure != null) return false;
        if (orderBy != null ? !orderBy.equals(that.orderBy) : that.orderBy != null) return false;
        return departureTimeStamp != null ? departureTimeStamp.equals(that.departureTimeStamp) : that.departureTimeStamp == null;
    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
        result = 31 * result + (departureTimeStamp != null ? departureTimeStamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "G1FlightSearchRequest{" +
                "event='" + event + '\'' +
                ", destination='" + destination + '\'' +
                ", departure='" + departure + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", departureTimeStamp=" + departureTimeStamp +
                ", filterBy=" + filterBy +
                '}';
    }
}
