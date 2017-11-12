package fr.unice.polytech.hcs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Explanation implements Serializable {

    @JsonProperty public String explanation;
    @JsonProperty public String travelId;

    public Explanation() {
    }

    public Explanation(String explanation, String travelId) {
        this.explanation = explanation;
        this.travelId = travelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Explanation that = (Explanation) o;

        if (explanation != null ? !explanation.equals(that.explanation) : that.explanation != null) return false;
        return travelId != null ? travelId.equals(that.travelId) : that.travelId == null;
    }

    @Override
    public int hashCode() {
        int result = explanation != null ? explanation.hashCode() : 0;
        result = 31 * result + (travelId != null ? travelId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Explanation{" +
                "explanation='" + explanation + '\'' +
                ", travelId='" + travelId + '\'' +
                '}';
    }
}
