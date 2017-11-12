package fr.unice.polytech.hcs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Travel implements Serializable {

    @JsonProperty public String travelId;
    @JsonProperty public List<Expense> documents;
    @JsonProperty public String status;
    @JsonProperty public String explanation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Travel travel = (Travel) o;

        if (travelId != null ? !travelId.equals(travel.travelId) : travel.travelId != null) return false;
        if (documents != null ? !documents.equals(travel.documents) : travel.documents != null) return false;
        if (status != null ? !status.equals(travel.status) : travel.status != null) return false;
        return explanation != null ? explanation.equals(travel.explanation) : travel.explanation == null;
    }

    @Override
    public int hashCode() {
        int result = travelId != null ? travelId.hashCode() : 0;
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (explanation != null ? explanation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "travelId='" + travelId + '\'' +
                ", documents=" + documents +
                ", status='" + status + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
