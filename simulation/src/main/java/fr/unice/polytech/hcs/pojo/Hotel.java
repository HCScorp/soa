package fr.unice.polytech.hcs.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Hotel implements Serializable {

    @JsonProperty public String name;
    @JsonProperty public String city;
    @JsonProperty public String zipCode;
    @JsonProperty public String address;
    @JsonProperty public Double nightPrice;

    public Hotel() {
        // empty
    }

    public Hotel(String name, String city,
                 String zipCode, String address,
                 Double nightPrice) {
        this.name = name;
        this.city = city;
        this.zipCode = zipCode;
        this.address = address;
        this.nightPrice = nightPrice;
    }

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
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", address='" + address + '\'' +
                ", nightPrice=" + nightPrice +
                '}';
    }
}
