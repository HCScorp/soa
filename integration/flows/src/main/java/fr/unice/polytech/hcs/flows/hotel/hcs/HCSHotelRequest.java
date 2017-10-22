package fr.unice.polytech.hcs.flows.hotel.hcs;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.hotel.HotelRequest;

import java.io.Serializable;

public class HCSHotelRequest implements Serializable {

    @JsonProperty public String city;
    @JsonProperty public String dateFrom;
    @JsonProperty public String dateTo;
    @JsonProperty public String order;

    public HCSHotelRequest(HotelRequest hotelRequest) {
        this.city = hotelRequest.city;
        this.dateFrom = hotelRequest.dateFrom;
        this.dateTo = hotelRequest.dateTo;
        this.order = hotelRequest.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HCSHotelRequest that = (HCSHotelRequest) o;

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
