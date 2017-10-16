package fr.unice.polytech.hcs.flows.hcs.car;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Car implements Serializable {

    @JsonProperty("company")
    private String company;

    @JsonProperty("city")
    private String city;

    @JsonProperty("model")
    private String model;

    @JsonProperty("numberPlate")
    private String numberPlate;

    @JsonProperty("bookedDay")
    private List<LocalDate> bookedDay;


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public List<LocalDate> getBookedDay() {
        return bookedDay;
    }

    public void setBookedDay(List<LocalDate> bookedDay) {
        this.bookedDay = bookedDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (company != null ? !company.equals(car.company) : car.company != null) return false;
        if (city != null ? !city.equals(car.city) : car.city != null) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        return numberPlate != null ? numberPlate.equals(car.numberPlate) : car.numberPlate == null;
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (numberPlate != null ? numberPlate.hashCode() : 0);
        return result;
    }
}
