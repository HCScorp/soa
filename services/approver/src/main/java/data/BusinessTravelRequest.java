package data;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessTravelRequest {
    // May be missing some fields

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
        flights = ((List<Document>)document.get("flights"))
                .stream()
                .map(Flight::new)
                .collect(Collectors.toList());

        hotels = ((List<Document>)document.get("hotels"))
                .stream()
                .map(Hotel::new)
                .collect(Collectors.toList());


        cars = ((List<Document>)document.get("car"))
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

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
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
}
