package fr.unice.polytech.hcs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class HotelSearchRequest implements Serializable {

    @JsonProperty public String city;
    @JsonProperty public String dateFrom;
    @JsonProperty public String dateTo;
    @JsonProperty public String order;

    public HotelSearchRequest() {
    }

    public HotelSearchRequest(String city, String dateFrom, String dateTo, String order) {
        this.city = city;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelSearchRequest that = (HotelSearchRequest) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null) return false;
        return order != null ? order.equals(that.order) : that.order == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HotelSearchRequest{" +
                "city='" + city + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
