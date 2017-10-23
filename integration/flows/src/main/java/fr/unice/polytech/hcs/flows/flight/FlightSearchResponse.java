package fr.unice.polytech.hcs.flows.flight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class FlightSearchResponse implements Serializable, Iterable<Flight> {

    @JsonProperty public Flight[] result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightSearchResponse that = (FlightSearchResponse) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(result);
    }

    public int length() {
        return result.length;
    }

    @Override
    public Iterator<Flight> iterator() {
        return Arrays.asList(result).iterator();
    }
}
