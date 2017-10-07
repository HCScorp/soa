package data;

import java.time.LocalDate;
import java.util.List;

public class Car {

    private String company;
    private String city;
    private String model;
    private String numberPlate;
    private List<LocalDate> bookedDays;

    public Car() {
        // empty
    }

    public Car(String company, String city, String model, String numberPlate, List<LocalDate> bookedDays) {
        this.company = company;
        this.city = city;
        this.model = model;
        this.numberPlate = numberPlate;
        this.bookedDays = bookedDays;
    }


    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getModel() {
        return model;
    }

    public List<LocalDate> getBookedDays() {
        return bookedDays;
    }

    public String getNumberPlate() {
        return numberPlate;
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
