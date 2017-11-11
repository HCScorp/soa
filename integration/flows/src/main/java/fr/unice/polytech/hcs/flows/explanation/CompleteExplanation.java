package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.expense.Travel;

import java.io.Serializable;

public class CompleteExplanation extends Travel {
    @JsonProperty public String explanation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CompleteExplanation that = (CompleteExplanation) o;

        return explanation != null ? explanation.equals(that.explanation) : that.explanation == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (explanation != null ? explanation.hashCode() : 0);
        return result;
    }
}
