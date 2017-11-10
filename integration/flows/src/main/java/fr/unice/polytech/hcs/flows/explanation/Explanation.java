package fr.unice.polytech.hcs.flows.explanation;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.expense.Travel;

import java.io.Serializable;

public class Explanation implements Serializable {

    @JsonProperty public String explanation;
    @JsonProperty public int id;

}
