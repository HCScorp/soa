package fr.unice.polytech.hcs.flows.car.g2;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class G2CarSearchRequest implements Serializable {

    @JsonProperty private String event;
    @JsonProperty private String destination;
    @JsonProperty private String departure;
    @JsonProperty private long departureTimeStamp;

    G2CarSearchRequest(CarSearchRequest csr) {
        // TODO
    }

    // TODO eq hash
}
