package fr.unice.polytech.hcs.flows.splitator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GenericResponse<T> implements Serializable, Iterable<T> {

    @JsonProperty
    public List<T> result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericResponse<?> that = (GenericResponse<?>) o;

        return result == null
                ? that.result == null
                : (result.containsAll(that.result) && that.result.containsAll(result));
    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }

    @Override
    public Iterator<T> iterator() {
        return result.iterator();
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "result=" + result.stream().map(Object::toString).collect(Collectors.joining(",")) +
                '}';
    }
}