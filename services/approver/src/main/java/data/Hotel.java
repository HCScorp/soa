package data;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Hotel {

    private String name;
    private String city;
    private String zipCode;
    private String address;
    private int nightPrice;
    private List<LocalDate> fullBookedDays;

    public Hotel() {
        // empty
    }

    public Hotel(String name, String city,
                 String zipCode, String address,
                 int nightPrice, List<LocalDate> fullBookedDays) {
        this.name = name;
        this.city = city;
        this.zipCode = zipCode;
        this.address = address;
        this.nightPrice = nightPrice;
        this.fullBookedDays = fullBookedDays;
    }

    public Hotel(Document bson) {
        this.name = bson.getString("name");
        this.nightPrice = bson.getInteger("nightPrice");
        this.fullBookedDays = ((List<Document>) bson.get("fullBookedDays")).stream()
                .map(d -> d.getString("date"))
                .map(LocalDate::parse)
                .collect(Collectors.toList());

        this.city = bson.getString("city");
        this.zipCode = bson.getString("zipCode");
        this.address = bson.getString("address");
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

    public int getNightPrice() {
        return nightPrice;
    }

    public List<LocalDate> getFullBookedDays() {
        return fullBookedDays;
    }

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();

        o.put("name", name);
        o.put("city", city);
        o.put("zipCode", zipCode);
        o.put("address", address);
        o.put("nightPrice", nightPrice);

        JSONArray a = new JSONArray();
        fullBookedDays.forEach(f -> a.put(f.toString()));

        o.put("fullBookedDays", a);

        return o;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (nightPrice != hotel.nightPrice) return false;
        if (!name.equals(hotel.name)) return false;
        if (!city.equals(hotel.city)) return false;
        if (!zipCode.equals(hotel.zipCode)) return false;
        return address.equals(hotel.address);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + nightPrice;
        return result;
    }
}
