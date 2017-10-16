package fr.unice.polytech.hcs.flows.hcs.car;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Car implements Serializable {


    @JsonProperty("city")
    private String city;

    @JsonProperty("dateTo")
    private String dateTo;

    @JsonProperty("dateFrom")
    private String dateFrom;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (city != null ? !city.equals(car.city) : car.city != null) return false;
        if (dateTo != null ? !dateTo.equals(car.dateTo) : car.dateTo != null) return false;
        return dateFrom != null ? dateFrom.equals(car.dateFrom) : car.dateFrom == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        return result;
    }

}
