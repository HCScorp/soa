package fr.unice.polytech.hcs.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CarSearchRequest implements Serializable {

    @JsonProperty public String city;
    @JsonProperty public String dateFrom;
    @JsonProperty public String dateTo;

    public CarSearchRequest() {
    }

    public CarSearchRequest(String city, String dateFrom, String dateTo) {
        this.city = city;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarSearchRequest that = (CarSearchRequest) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
        return dateTo != null ? dateTo.equals(that.dateTo) : that.dateTo == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarSearchRequest{" +
                "city='" + city + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }
}
