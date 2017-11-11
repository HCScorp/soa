package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.expense.Travel;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Approval implements Serializable {
    @JsonProperty public Travel travel;
    @JsonProperty public Integer sum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Approval approval = (Approval) o;

        if (travel != null ? !travel.equals(approval.travel) : approval.travel != null) return false;
        return sum != null ? sum.equals(approval.sum) : approval.sum == null;
    }

    @Override
    public int hashCode() {
        int result = travel != null ? travel.hashCode() : 0;
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "travel=" + travel +
                ", sum=" + sum +
                '}';
    }
}
