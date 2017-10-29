package fr.unice.polytech.hcs.flows.hotel.g7;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class G7HotelSearchRequest implements Serializable {

    @JsonProperty public String address;
    @JsonProperty public String city;
    @JsonProperty public String checkin;
    @JsonProperty public String checkout;
    @JsonProperty public String sortOrder;
    @JsonProperty public int resultNumber;

    G7HotelSearchRequest(HotelSearchRequest hsr) {
        this.city = hsr.city;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.checkin = LocalDate.parse(hsr.dateFrom).format(formatter);
        this.checkout = LocalDate.parse(hsr.dateTo).format(formatter);
        this.sortOrder = hsr.order;
        this.address = "";
        this.resultNumber = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        G7HotelSearchRequest that = (G7HotelSearchRequest) o;

        if (resultNumber != that.resultNumber) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (checkin != null ? !checkin.equals(that.checkin) : that.checkin != null) return false;
        if (checkout != null ? !checkout.equals(that.checkout) : that.checkout != null) return false;
        return sortOrder != null ? sortOrder.equals(that.sortOrder) : that.sortOrder == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (checkin != null ? checkin.hashCode() : 0);
        result = 31 * result + (checkout != null ? checkout.hashCode() : 0);
        result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
        result = 31 * result + resultNumber;
        return result;
    }
}
