package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
public class BusinessTravelRequest {
    // May be missing some fields

    private BusinessTravelRequestStatus status;
    private List<Flight> flghts;
    private List<Hotel> hotels;
    private List<Car> cars;

    @XmlElement(name = "status", required = true)
    public BusinessTravelRequestStatus getStatus() { return status; }
    public void setStatus(BusinessTravelRequestStatus status) { this.status = status; }

    @XmlElement
    public List<Flight> getFlghts() { return flghts; }
    public void setFlghts(List<Flight> flghts) { this.flghts = flghts; }

    @XmlElement
    public List<Hotel> getHotels() { return hotels; }
    public void setHotels(List<Hotel> hotels) { this.hotels = hotels; }

    @XmlElement
    public List<Car> getCars() { return cars; }
    public void setCars(List<Car> cars) { this.cars = cars; }
}
