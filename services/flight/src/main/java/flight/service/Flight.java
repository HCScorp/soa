package flight.service;

import org.bson.Document;

import java.time.Duration;
import java.time.LocalDate;

public class Flight {

    public enum JourneyType {
        DIRECT,
        INDIRECT
    }

    public enum Category {
        ECO,
        ECO_PREMIUM,
        BUSINESS,
        FIRST
    }

    private String origin;
    private String destination;
    private LocalDate date;
    private int price;
    private JourneyType journeyType;
    private Duration duration;
    private Category category;
    private String airline;

    public Flight() {
        // empty
    }

    public Flight(String origin, String destination,
                  LocalDate date, int price,
                  JourneyType journeyType, Duration duration,
                  Category category, String airline) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.price = price;
        this.journeyType = journeyType;
        this.duration = duration;
        this.category = category;
        this.airline = airline;
    }

    public Flight(Document bson) {
        this.origin = bson.getString("origin");
        this.destination = bson.getString("destination");
        this.date = LocalDate.parse(bson.getString("date"));
        this.price = bson.getInteger("price");
        this.journeyType = JourneyType.valueOf(bson.getString("journeyType"));
        this.duration = Duration.ofMinutes(bson.getLong("duration"));
        this.category = Category.valueOf(bson.getString("category"));
        this.airline = bson.getString("airline");
    }

    public Document toBson() {
        return new Document()
                .append("origin", origin)
                .append("destination", destination)
                .append("date", date.toString())
                .append("price", price)
                .append("journeyType", journeyType.toString())
                .append("duration", duration.toMinutes())
                .append("category", category.toString())
                .append("airline", airline);
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public JourneyType getJourneyType() {
        return journeyType;
    }

    public Duration getDuration() {
        return duration;
    }

    public Category getCategory() {
        return category;
    }

    public String getAirline() {
        return airline;
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
        if (journeyType != flight.journeyType) return false;
        if (duration != null ? !duration.equals(flight.duration) : flight.duration != null) return false;
        if (category != flight.category) return false;
        return airline != null ? airline.equals(flight.airline) : flight.airline == null;
    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (journeyType != null ? journeyType.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        return result;
    }
}
