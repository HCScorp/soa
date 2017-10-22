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
    // TODO
}
