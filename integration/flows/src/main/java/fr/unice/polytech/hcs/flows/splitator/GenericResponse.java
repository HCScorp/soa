package fr.unice.polytech.hcs.flows.splitator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class GenericResponse<T> implements Serializable, Iterable<T> {

    @JsonProperty public T[] result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericResponse<T> that = (GenericResponse<T>) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(result);
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(result).iterator();
    }
}