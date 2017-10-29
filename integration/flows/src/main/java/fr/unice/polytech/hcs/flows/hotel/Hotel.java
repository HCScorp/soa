package fr.unice.polytech.hcs.flows.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.splitator.SerializableComparable;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hotel implements SerializableComparable<Hotel> {

    @JsonProperty public String name;
    @JsonProperty public String city;
    @JsonProperty public String zipCode;
    @JsonProperty public String address;
    @JsonProperty public Double nightPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (name != null ? !name.equals(hotel.name) : hotel.name != null) return false;
        if (city != null ? !city.equals(hotel.city) : hotel.city != null) return false;
        if (zipCode != null ? !zipCode.equals(hotel.zipCode) : hotel.zipCode != null) return false;
        if (address != null ? !address.equals(hotel.address) : hotel.address != null) return false;
        return nightPrice != null ? nightPrice.equals(hotel.nightPrice) : hotel.nightPrice == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (nightPrice != null ? nightPrice.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Hotel o) {
        return nightPrice.compareTo(o.nightPrice);
    }
}
