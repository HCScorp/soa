package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Explanation implements Serializable {

    @JsonProperty public String explanation;
    @JsonProperty public String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Explanation that = (Explanation) o;

        if (explanation != null ? !explanation.equals(that.explanation) : that.explanation != null) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = explanation != null ? explanation.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
