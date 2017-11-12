package fr.unice.polytech.hcs.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BtrResponse implements Serializable {
    @JsonProperty public String _id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BtrResponse that = (BtrResponse) o;

        return _id != null ? _id.equals(that._id) : that._id == null;
    }

    @Override
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BtrResponse{" +
                "_id='" + _id + '\'' +
                '}';
    }
}
