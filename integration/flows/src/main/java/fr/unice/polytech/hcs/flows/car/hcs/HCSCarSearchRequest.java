package fr.unice.polytech.hcs.flows.car.hcs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HCSCarSearchRequest implements Serializable {

    @JsonProperty
    private String city;
    @JsonProperty
    private String dateFrom;
    @JsonProperty
    private String dateTo;

    HCSCarSearchRequest(CarSearchRequest csr) {
        this.city = csr.city;
        this.dateFrom = csr.dateFrom;
        this.dateTo = csr.dateTo;
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
        return "HCSCarSearchRequest{" +
                "city='" + city + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }
}
