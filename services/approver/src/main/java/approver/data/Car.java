package approver.data;

import org.bson.Document;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Car {

    private String company;
    private String city;
    private String model;
    private String numberPlate;
    private Double price;
    private List<LocalDate> bookedDays;

    public Car() {
        // empty
    }

    public Car(String company, String city, String model, String numberPlate, Double price, List<LocalDate> bookedDays) {
        this.company = company;
        this.city = city;
        this.model = model;
        this.price = price;
        this.numberPlate = numberPlate;
        this.bookedDays = bookedDays;
    }

    public Car(Document bson) {
        this.company = bson.getString("company");
        this.city = bson.getString("city");
        this.model = bson.getString("model");
        this.numberPlate = bson.getString("numberPlate");
        this.price = bson.getDouble("price");
        this.bookedDays = ((List<Document>) bson.get("bookedDays")).stream()
                .map(d -> d.getString("date"))
                .map(LocalDate::parse)
                .collect(Collectors.toList());
    }

    public Document toBson() {
        return new Document()
                .append("company", company)
                .append("city", city)
                .append("model", model)
                .append("numberPlate", numberPlate)
                .append("price", price)
                .append("bookedDays", bookedDays.stream()
                        .map(LocalDate::toString)
                        .map(d -> new Document("date", d))
                        .collect(Collectors.toList()));
    }

    public static JSONObject convertToWebResult(Document carBson) {
        carBson.remove("bookedDays");
        carBson.remove("_id");
        return new JSONObject(carBson.toJson());
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

    public String getNumberPlate() {
        return numberPlate;
    }

    public Double getPrice() {
        return price;
    }

    public List<LocalDate> getBookedDays() {
        return bookedDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (price != null ? !price.equals(car.price) : car.price != null) return false;
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
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (bookedDays != null ? bookedDays.hashCode() : 0);
        return result;
    }
}
