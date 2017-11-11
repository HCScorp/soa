package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExplanationAnswer  {
    @JsonProperty public String id;
    @JsonProperty public int code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExplanationAnswer that = (ExplanationAnswer) o;

        if (code != that.code) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + code;
        return result;
    }
}
