package fr.unice.polytech.hcs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Flight implements Serializable {

    @JsonProperty public String origin;
    @JsonProperty public String destination;
    @JsonProperty public String date;
    @JsonProperty public String time;
    @JsonProperty public Double price;
    @JsonProperty public String journeyType;
    @JsonProperty public Integer duration;
    @JsonProperty public String category;
    @JsonProperty public String airline;

    public Flight() {
        // empty
    }

    public Flight(String origin, String destination,
                  String date, String time,
                  Double price,
                  String journeyType, Integer duration,
                  String category, String airline) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.journeyType = journeyType;
        this.duration = duration;
        this.category = category;
        this.airline = airline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (origin != null ? !origin.equals(flight.origin) : flight.origin != null) return false;
        if (destination != null ? !destination.equals(flight.destination) : flight.destination != null) return false;
        if (date != null ? !date.equals(flight.date) : flight.date != null) return false;
        if (time != null ? !time.equals(flight.time) : flight.time != null) return false;
        if (price != null ? !price.equals(flight.price) : flight.price != null) return false;
        if (journeyType != null ? !journeyType.equals(flight.journeyType) : flight.journeyType != null) return false;
        if (duration != null ? !duration.equals(flight.duration) : flight.duration != null) return false;
        if (category != null ? !category.equals(flight.category) : flight.category != null) return false;
        return airline != null ? airline.equals(flight.airline) : flight.airline == null;
    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (journeyType != null ? journeyType.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", price=" + price +
                ", journeyType='" + journeyType + '\'' +
                ", duration=" + duration +
                ", category='" + category + '\'' +
                ", airline='" + airline + '\'' +
                '}';
    }
}
