package fr.unice.polytech.hcs.flows.flight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

public class FlightSearchResponse implements Serializable {

    @JsonProperty public Flight[] flights;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightSearchResponse that = (FlightSearchResponse) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(flights, that.flights);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(flights);
    }
}
