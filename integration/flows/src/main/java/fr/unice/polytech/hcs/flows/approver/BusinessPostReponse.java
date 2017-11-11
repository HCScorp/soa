package fr.unice.polytech.hcs.flows.approver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BusinessPostReponse implements Serializable {
    @JsonProperty public String _id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessPostReponse that = (BusinessPostReponse) o;

        return _id != null ? _id.equals(that._id) : that._id == null;
    }

    @Override
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }
}
