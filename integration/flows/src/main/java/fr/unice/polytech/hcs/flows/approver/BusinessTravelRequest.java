package fr.unice.polytech.hcs.flows.approver;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.car.Car;
import fr.unice.polytech.hcs.flows.flight.Flight;
import fr.unice.polytech.hcs.flows.hotel.Hotel;

import java.io.Serializable;
import java.util.List;

public class BusinessTravelRequest implements Serializable {
    @JsonProperty public int id;
    @JsonProperty public String status;
    @JsonProperty public List<Flight> flights;
    @JsonProperty public List<Hotel> hotels;
    @JsonProperty public List<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTravelRequest that = (BusinessTravelRequest) o;

        if (id != that.id) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (flights != null ? !flights.equals(that.flights) : that.flights != null) return false;
        if (hotels != null ? !hotels.equals(that.hotels) : that.hotels != null) return false;
        return cars != null ? cars.equals(that.cars) : that.cars == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (flights != null ? flights.hashCode() : 0);
        result = 31 * result + (hotels != null ? hotels.hashCode() : 0);
        result = 31 * result + (cars != null ? cars.hashCode() : 0);
        return result;
    }
}
