package fr.unice.polytech.hcs.flows.flight.hcs;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;

import java.io.Serializable;

public class HCSFlightSearchRequest implements Serializable {

    @JsonProperty("origin")         private String origin;
    @JsonProperty("destination")    private String destination;
    @JsonProperty("date")           private String date;
    @JsonProperty("timeFrom")       private String timeFrom;
    @JsonProperty("timeTo")         private String timeTo;
    @JsonProperty("journeyType")    private String journeyType;
    @JsonProperty("maxTravelTime")  private int maxTravelTime;
    @JsonProperty("category")       private String category;
    @JsonProperty("airline")        private String airline;
    @JsonProperty("order")          private String order;

    public HCSFlightSearchRequest(FlightSearchRequest fsr) {
        origin = fsr.origin;
        destination = fsr.destination;
        date = fsr.date;
        timeFrom = fsr.timeFrom;
        timeTo = fsr.timeTo;
        journeyType = fsr.journeyType;
        maxTravelTime = fsr.maxTravelTime;
        category = fsr.category;
        airline = fsr.airline;
        order = fsr.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HCSFlightSearchRequest that = (HCSFlightSearchRequest) o;

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
