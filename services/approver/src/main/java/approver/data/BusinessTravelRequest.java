package approver.data;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessTravelRequest {

    private int id;
    private BusinessTravelRequestStatus status = BusinessTravelRequestStatus.WAITING;
    private List<Flight> flights;
    private List<Hotel> hotels;
    private List<Car> cars;

    public BusinessTravelRequest(){
        flights = new ArrayList<>();
        hotels = new ArrayList<>();
        cars = new ArrayList<>();
    }

    public BusinessTravelRequest(Document document){
        id = document.getInteger("id");
        status = BusinessTravelRequestStatus.valueOf(document.getString("status"));

        flights = ((List<Document>)document.get("flights"))
                .stream()
                .map(Flight::new)
                .collect(Collectors.toList());

        hotels = ((List<Document>)document.get("hotels"))
                .stream()
                .map(Hotel::new)
                .collect(Collectors.toList());

        cars = ((List<Document>)document.get("cars"))
                .stream()
                .map(Car::new)
                .collect(Collectors.toList());

    }

    public BusinessTravelRequestStatus getStatus() { return status; }
    public void setStatus(BusinessTravelRequestStatus status) { this.status = status; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flghts) { this.flights = flghts; }

    public List<Hotel> getHotels() { return hotels; }
    public void setHotels(List<Hotel> hotels) { this.hotels = hotels; }

    public List<Car> getCars() { return cars; }
    public void setCars(List<Car> cars) { this.cars = cars; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();

        o.put("id", id);

        o.put("status", status.toString());

        JSONArray c = new JSONArray();
        cars.forEach(e -> c.put(e.toJSON()));
        o.put("cars", c);

        JSONArray f = new JSONArray();
        flights.forEach(fl -> f.put(fl.toJSON()));
        o.put("flights", f);

        JSONArray h = new JSONArray();
        hotels.forEach(ho -> h.put(ho.toJSON()));
        o.put("hotels", hotels);

        return o;
    }

    public Document toBSON(){
        return Document.parse(toJSON().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessTravelRequest)) return false;

        BusinessTravelRequest that = (BusinessTravelRequest) o;

        if (getId() != that.getId()) return false;
        if (getStatus() != that.getStatus()) return false;
        if (getFlights() != null ? !getFlights().equals(that.getFlights()) : that.getFlights() != null) return false;
        if (getHotels() != null ? !getHotels().equals(that.getHotels()) : that.getHotels() != null) return false;
        return getCars() != null ? getCars().equals(that.getCars()) : that.getCars() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getFlights() != null ? getFlights().hashCode() : 0);
        result = 31 * result + (getHotels() != null ? getHotels().hashCode() : 0);
        result = 31 * result + (getCars() != null ? getCars().hashCode() : 0);
        return result;
    }
}
