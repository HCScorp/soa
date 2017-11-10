package fr.unice.polytech.hcs.flows.car.g2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class G2Car {
    @JsonProperty
    public Double price;
    @JsonProperty
    public String name;

    @Override
    public String toString() {
        return "G2Car{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}