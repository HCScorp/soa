package fr.unice.polytech.hcs.flows.flight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FlightSearchRequest implements Serializable {

    @JsonProperty("origin")
    private String origin;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("date")
    private String date;
    @JsonProperty("timeFrom")
    private String timeFrom;
    @JsonProperty("timeTo")
    private String timeTo;
    @JsonProperty("journeyType")
    private String journeyType;
    @JsonProperty("maxTravelTime")
    private int maxTravelTime;
    @JsonProperty("category")
    private String category;
    @JsonProperty("airline")
    private String airline;
    @JsonProperty("order")
    private String order;

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

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public int getMaxTravelTime() {
        return maxTravelTime;
    }

    public void setMaxTravelTime(int maxTravelTime) {
        this.maxTravelTime = maxTravelTime;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightSearchRequest that = (FlightSearchRequest) o;

        if (maxTravelTime != that.maxTravelTime) return false;
        if (origin != null ? !origin.equals(that.origin) : that.origin != null) return false;
        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (timeFrom != null ? !timeFrom.equals(that.timeFrom) : that.timeFrom != null) return false;
        if (timeTo != null ? !timeTo.equals(that.timeTo) : that.timeTo != null) return false;
        if (journeyType != null ? !journeyType.equals(that.journeyType) : that.journeyType != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (airline != null ? !airline.equals(that.airline) : that.airline != null) return false;
        return order != null ? order.equals(that.order) : that.order == null;
    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (timeFrom != null ? timeFrom.hashCode() : 0);
        result = 31 * result + (timeTo != null ? timeTo.hashCode() : 0);
        result = 31 * result + (journeyType != null ? journeyType.hashCode() : 0);
        result = 31 * result + maxTravelTime;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
