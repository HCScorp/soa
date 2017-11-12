package fr.unice.polytech.hcs.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseReport implements Serializable {

    @JsonProperty public String travelId;
    @JsonProperty public List<Expense> documents;

    public ExpenseReport() {
    }

    public ExpenseReport(String travelId, List<Expense> documents) {
        this.travelId = travelId;
        this.documents = documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseReport that = (ExpenseReport) o;

        if (travelId != null ? !travelId.equals(that.travelId) : that.travelId != null) return false;
        return documents != null ? documents.containsAll(that.documents) : that.documents == null;
    }

    @Override
    public int hashCode() {
        int result = travelId != null ? travelId.hashCode() : 0;
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExpenseReport{" +
                "travelId='" + travelId + '\'' +
                ", documents=" + documents.stream()
                                            .map(Object::toString)
                                            .collect(Collectors.joining(", ")) +
                '}';
    }
}
