package fr.unice.polytech.hcs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessTravelRequest implements Serializable {

    @JsonProperty public String status;
    @JsonProperty public List<Flight> flights;
    @JsonProperty public List<Hotel> hotels;
    @JsonProperty public List<Car> cars;


    public BusinessTravelRequest() {
    }

    public BusinessTravelRequest(List<Flight> flights, List<Hotel> hotels, List<Car> cars) {
        this.flights = flights;
        this.hotels = hotels;
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTravelRequest that = (BusinessTravelRequest) o;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (flights != null ? !flights.equals(that.flights) : that.flights != null) return false;
        if (hotels != null ? !hotels.equals(that.hotels) : that.hotels != null) return false;
        return cars != null ? cars.equals(that.cars) : that.cars == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (flights != null ? flights.hashCode() : 0);
        result = 31 * result + (hotels != null ? hotels.hashCode() : 0);
        result = 31 * result + (cars != null ? cars.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BusinessTravelRequest{" +
                "status='" + status + '\'' +
                ", flights=" + flights +
                ", hotels=" + hotels +
                ", cars=" + cars +
                '}';
    }
}
