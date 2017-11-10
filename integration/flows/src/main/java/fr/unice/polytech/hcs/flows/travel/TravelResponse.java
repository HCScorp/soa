package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.expense.Document;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelResponse implements Serializable {

    @JsonProperty public int travelId;
    //@JsonProperty public String status;
    @JsonProperty public List<Document> documents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelResponse response = (TravelResponse) o;

        if (travelId != response.travelId) return false;
        return documents != null ? documents.equals(response.documents) : response.documents == null;
    }

    @Override
    public int hashCode() {
        int result = travelId;
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TravelResponse{" +
                "travelId=" + travelId +
                ", documents=" + documents +
                '}';
    }
}
