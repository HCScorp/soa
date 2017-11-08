package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Expense {
    @JsonProperty public int travelId;
    @JsonProperty public List<Document> documents;
}
