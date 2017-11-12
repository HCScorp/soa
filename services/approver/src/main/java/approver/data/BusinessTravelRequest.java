package approver.data;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessTravelRequest {

    @XmlType
    public enum Status {
        WAITING, APPROVED, DENIED
    }

    private ObjectId id;
    private Status status = Status.WAITING;
    private List<Flight> flights;
    private List<Hotel> hotels;
    private List<Car> cars;

    public BusinessTravelRequest() {
        this.id = new ObjectId();
        this.flights = new ArrayList<>();
        this.hotels = new ArrayList<>();
        this.cars = new ArrayList<>();
    }

    public BusinessTravelRequest(Document document) {
        this.id = document.getObjectId("_id");
        this.status = Status.valueOf(document.getString("status"));

        this.flights = ((List<Document>) document.get("flights")).stream()
                .map(Flight::new)
                .collect(Collectors.toList());

        this.hotels = ((List<Document>) document.get("hotels")).stream()
                .map(Hotel::new)
                .collect(Collectors.toList());

        this.cars = ((List<Document>) document.get("cars")).stream()
                .map(Car::new)
                .collect(Collectors.toList());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void approve() {
        this.status = Status.APPROVED;
    }

    public void deny() {
        this.status = Status.DENIED;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<Car> getCars() {
        return cars;
    }

    public Document toBSON() {
        return new Document()
                .append("_id", id)
                .append("status", status.toString())
                .append("flights", flights.stream()
                        .map(Flight::toBson)
                        .collect(Collectors.toList()))
                .append("hotels", hotels.stream()
                        .map(Hotel::toBson)
                        .collect(Collectors.toList()))
                .append("cars", cars.stream()
                        .map(Car::toBson)
                        .collect(Collectors.toList()));
    }

    public static BusinessTravelRequest fromExternalJson(String jsonStr) throws JSONException {
        Document bson = Document.parse(jsonStr);
        BusinessTravelRequest result = new BusinessTravelRequest();

        result.flights = ((List<Document>) bson.get("flights")).stream()
                .map(Flight::new)
                .collect(Collectors.toList());

        result.hotels = ((List<Document>) bson.get("hotels")).stream()
                .map(Hotel::new)
                .collect(Collectors.toList());

        result.cars = ((List<Document>) bson.get("cars")).stream()
                .map(Car::new)
                .collect(Collectors.toList());

        return result;
    }

    public JSONObject toJson() {
        return new JSONObject(toBSON().toJson());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTravelRequest that = (BusinessTravelRequest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
