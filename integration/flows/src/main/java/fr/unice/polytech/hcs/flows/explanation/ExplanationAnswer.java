package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ExplanationAnswer implements Serializable {

    @JsonProperty public String travelId;
    @JsonProperty public boolean acceptRefund;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExplanationAnswer that = (ExplanationAnswer) o;

        if (acceptRefund != that.acceptRefund) return false;
        return travelId != null ? travelId.equals(that.travelId) : that.travelId == null;
    }

    @Override
    public int hashCode() {
        int result = travelId != null ? travelId.hashCode() : 0;
        result = 31 * result + (acceptRefund ? 1 : 0);
        return result;
    }
}
