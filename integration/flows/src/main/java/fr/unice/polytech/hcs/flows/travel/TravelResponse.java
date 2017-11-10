package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelResponse implements Serializable {

    @JsonProperty public Integer travelId;
    @JsonProperty public String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TravelResponse response = (TravelResponse) o;

        if (travelId != null ? !travelId.equals(response.travelId) : response.travelId != null) return false;
        return status != null ? status.equals(response.status) : response.status == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (travelId != null ? travelId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TravelResponse{" +
                "travelId=" + travelId +
                ", status='" + status + '\'' +
                '}';
    }
}
