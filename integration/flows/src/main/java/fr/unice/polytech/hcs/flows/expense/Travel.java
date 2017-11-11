package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Travel implements Serializable {
    @JsonProperty public int travelId;
    @JsonProperty public List<Expense> documents;
    @JsonProperty public Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Travel travel = (Travel) o;

        if (travelId != travel.travelId) return false;
        if (documents != null ? !documents.equals(travel.documents) : travel.documents != null) return false;
        return status == travel.status;
    }

    @Override
    public int hashCode() {
        int result = travelId;
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
