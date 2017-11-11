package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelRequest implements Serializable {

    @JsonProperty public String travelId;

    public TravelRequest(){}

    public TravelRequest(String travelId) {
        this.travelId = travelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelRequest that = (TravelRequest) o;

        return travelId != null ? travelId.equals(that.travelId) : that.travelId == null;
    }

    @Override
    public int hashCode() {
        return travelId != null ? travelId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TravelRequest{" +
                "travelId='" + travelId + '\'' +
                '}';
    }
}
