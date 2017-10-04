package hotel.service;

import org.bson.Document;

import java.util.Date;
import java.util.List;

public class Hotel {

    String name;
    String city;
    String zipCode;
    String address;
    int nightPrice;
    List<Date> fullBookedDays;

    public Hotel() {
        // empty
    }

    public Hotel(Document bson) {
        this.name = bson.getString("name");
        this.nightPrice = bson.getInteger("night_price");
        this.fullBookedDays = (List<Date>) bson.get("full_booked_days");

        this.city = bson.getString("city");
        this.zipCode = bson.getString("zip_code");
        this.address = bson.getString("address");
    }


    public Document toBson() {
        Document result = new Document();
        result.put("name", name);
        result.put("night_price", nightPrice);
        result.put("full_booked_days", fullBookedDays);

        result.put("city", city);
        result.put("zip_code", zipCode);
        result.put("address", address);
        return result;
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

    public List<Date> getFullBookedDays() {
        return fullBookedDays;
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
