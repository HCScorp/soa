package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ExpenseReportDB implements Serializable {
    @JsonProperty public String _id;
    @JsonProperty public String travelId;
    @JsonProperty public List<Expense> documents;

    public ExpenseReportDB(){}

    public ExpenseReportDB(ExpenseReport report){
        travelId = report.travelId;
        _id = report.travelId;
        documents = report.documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseReportDB that = (ExpenseReportDB) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (travelId != null ? !travelId.equals(that.travelId) : that.travelId != null) return false;
        return documents != null ? documents.containsAll(that.documents) : that.documents == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (travelId != null ? travelId.hashCode() : 0);
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        return result;
    }
}
