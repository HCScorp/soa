package scenarios.data;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public Car(Document bson) {
        this.company = bson.getString("company");
        this.city = bson.getString("city");
        this.model = bson.getString("model");
        this.numberPlate = bson.getString("numberPlate");
        this.bookedDays = ((List<Document>) bson.get("bookedDays")).stream()
                .map(d -> d.getString("date"))
                .map(LocalDate::parse)
                .collect(Collectors.toList());
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

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("company", company);
        o.put("city", city);
        o.put("model", model);
        o.put("numberPlate", numberPlate);
        JSONArray bd = new JSONArray();
        bookedDays.forEach(bd::put);

        o.put("bookedDays", bd);

        return o;
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
