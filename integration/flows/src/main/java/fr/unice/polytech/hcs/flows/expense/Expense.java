package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense implements Serializable {

    @JsonProperty public String category;
    @JsonProperty public String evidence;
    @JsonProperty public Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (category != null ? !category.equals(expense.category) : expense.category != null) return false;
        if (evidence != null ? !evidence.equals(expense.evidence) : expense.evidence != null) return false;
        return price != null ? price.equals(expense.price) : expense.price == null;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (evidence != null ? evidence.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "category='" + category + '\'' +
                ", evidence='" + evidence + '\'' +
                ", price=" + price +
                '}';
    }
}
