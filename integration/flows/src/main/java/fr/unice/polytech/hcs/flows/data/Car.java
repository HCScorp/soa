package fr.unice.polytech.hcs.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Car implements Serializable {

    @JsonProperty("compagny")
    private String compagny;
    @JsonProperty("city")
    private String city;
    @JsonProperty("model")
    private String model;
    @JsonProperty("numberPlate")
    private String numberPlate;
    @JsonProperty("bookedDay")
    private String bookedDay;


    public String getCompagny() {

        return compagny;
    }

    public void setCompagny(String compagny) {
        this.compagny = compagny;
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

    public String getBookedDay() {
        return bookedDay;
    }

    public void setBookedDay(String bookedDay) {
        this.bookedDay = bookedDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (compagny != null ? !compagny.equals(car.compagny) : car.compagny != null) return false;
        if (city != null ? !city.equals(car.city) : car.city != null) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (numberPlate != null ? !numberPlate.equals(car.numberPlate) : car.numberPlate != null) return false;
        return bookedDay != null ? bookedDay.equals(car.bookedDay) : car.bookedDay == null;
    }

    @Override
    public int hashCode() {
        int result = compagny != null ? compagny.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (numberPlate != null ? numberPlate.hashCode() : 0);
        result = 31 * result + (bookedDay != null ? bookedDay.hashCode() : 0);
        return result;
    }
}
