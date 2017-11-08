package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Expense implements Serializable {
    @JsonProperty public Integer travelId;
    @JsonProperty public List<Document> documents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (travelId != expense.travelId) return false;
        return documents != null ? documents.equals(expense.documents) : expense.documents == null;
    }

    @Override
    public int hashCode() {
        int result = travelId;
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "travelId=" + travelId +
                ", documents=" + documents +
                '}';
    }
}
