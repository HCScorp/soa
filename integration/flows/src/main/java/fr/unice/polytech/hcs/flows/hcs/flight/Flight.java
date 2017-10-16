package fr.unice.polytech.hcs.flows.hcs.flight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Duration;

public class Flight implements Serializable {

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;


    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;

    @JsonProperty("secondOfDay")
    private String secondOfDay;

    @JsonProperty("price")
    private int price;

    @JsonProperty("journeyType")
    private String journeyType;


    @JsonProperty("duration")
    private Duration duration;

    @JsonProperty("category")
    private String category;


    @JsonProperty("airline")
    private String airline;


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSecondOfDay() {
        return secondOfDay;
    }

    public void setSecondOfDay(String secondOfDay) {
        this.secondOfDay = secondOfDay;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (price != flight.price) return false;
        if (origin != null ? !origin.equals(flight.origin) : flight.origin != null) return false;
        if (destination != null ? !destination.equals(flight.destination) : flight.destination != null) return false;
        if (date != null ? !date.equals(flight.date) : flight.date != null) return false;
        if (time != null ? !time.equals(flight.time) : flight.time != null) return false;
        if (secondOfDay != null ? !secondOfDay.equals(flight.secondOfDay) : flight.secondOfDay != null) return false;
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
        result = 31 * result + (secondOfDay != null ? secondOfDay.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (journeyType != null ? journeyType.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        return result;
    }
}
