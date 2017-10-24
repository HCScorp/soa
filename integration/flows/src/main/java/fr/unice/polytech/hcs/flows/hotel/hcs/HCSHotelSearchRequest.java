package fr.unice.polytech.hcs.flows.hotel.hcs;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;

import java.io.Serializable;

public class HCSHotelSearchRequest implements Serializable {

    @JsonProperty private String city;
    @JsonProperty private String dateFrom;
    @JsonProperty private String dateTo;
    @JsonProperty private String order;

    HCSHotelSearchRequest(HotelSearchRequest hsr) {
        this.city = hsr.city;
        this.dateFrom = hsr.dateFrom;
        this.dateTo = hsr.dateTo;
        this.order = hsr.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HCSHotelSearchRequest that = (HCSHotelSearchRequest) o;

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
}
