package hotel.service;

import org.bson.Document;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Hotel {

    private String name;
    private String city;
    private String zipCode;
    private String address;
    private Double nightPrice;
    private List<LocalDate> fullBookedDays;

    public Hotel() {
        // empty
    }

    public Hotel(String name, String city,
                 String zipCode, String address,
                 Double nightPrice, List<LocalDate> fullBookedDays) {
        this.name = name;
        this.city = city;
        this.zipCode = zipCode;
        this.address = address;
        this.nightPrice = nightPrice;
        this.fullBookedDays = fullBookedDays;
    }

    public Hotel(Document bson) {
        this.name = bson.getString("name");
        this.nightPrice = bson.getDouble("nightPrice");
        this.fullBookedDays = ((List<Document>) bson.get("fullBookedDays")).stream()
                .map(d -> d.getString("date"))
                .map(LocalDate::parse)
                .collect(Collectors.toList());

        this.city = bson.getString("city");
        this.zipCode = bson.getString("zipCode");
        this.address = bson.getString("address");
    }

    public Document toBson() {
        return new Document()
                .append("name", name)
                .append("nightPrice", nightPrice)
                .append("fullBookedDays", fullBookedDays.stream()
                        .map(LocalDate::toString)
                        .map(d -> new Document("date", d))
                        .collect(Collectors.toList()))
                .append("city", city)
                .append("zipCode", zipCode).append("address", address);
    }

    public static JSONObject convertToWebResult(Document hotelBson) {
        hotelBson.remove("fullBookedDays");
        hotelBson.remove("_id");
        return new JSONObject(hotelBson.toJson());
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddress() {
        return address;
    }

    public Double getNightPrice() {
        return nightPrice;
    }

    public List<LocalDate> getFullBookedDays() {
        return fullBookedDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (name != null ? !name.equals(hotel.name) : hotel.name != null) return false;
        if (city != null ? !city.equals(hotel.city) : hotel.city != null) return false;
        if (zipCode != null ? !zipCode.equals(hotel.zipCode) : hotel.zipCode != null) return false;
        if (address != null ? !address.equals(hotel.address) : hotel.address != null) return false;
        if (nightPrice != null ? !nightPrice.equals(hotel.nightPrice) : hotel.nightPrice != null) return false;
        return fullBookedDays != null ? fullBookedDays.equals(hotel.fullBookedDays) : hotel.fullBookedDays == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (nightPrice != null ? nightPrice.hashCode() : 0);
        result = 31 * result + (fullBookedDays != null ? fullBookedDays.hashCode() : 0);
        return result;
    }
}
