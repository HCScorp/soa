package fr.unice.polytech.hcs.flows.flight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FlightSearchRequest implements Serializable {

    @JsonProperty public String origin;
    @JsonProperty public String destination;
    @JsonProperty public String date;
    @JsonProperty public String timeFrom;
    @JsonProperty public String timeTo;
    @JsonProperty public String journeyType;
    @JsonProperty public Integer maxTravelTime; // in minutes
    @JsonProperty public String category;
    @JsonProperty public String airline;
    @JsonProperty public String order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightSearchRequest that = (FlightSearchRequest) o;

        if (maxTravelTime != null ? !maxTravelTime.equals(that.maxTravelTime) : that.maxTravelTime != null) return false;
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
        result = 31 * result + (maxTravelTime != null ? maxTravelTime.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlightSearchRequest{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", journeyType='" + journeyType + '\'' +
                ", maxTravelTime=" + maxTravelTime +
                ", category='" + category + '\'' +
                ", airline='" + airline + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
