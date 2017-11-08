package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {
    @JsonProperty public String category;
    @JsonProperty public String evidence;
}
