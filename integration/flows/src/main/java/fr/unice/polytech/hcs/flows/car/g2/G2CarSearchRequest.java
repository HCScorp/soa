package fr.unice.polytech.hcs.flows.car.g2;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;

import java.io.Serializable;

public class G2CarSearchRequest implements Serializable {

    @JsonProperty public String address;
    @JsonProperty public String checkin;
    @JsonProperty public String checkout;
    @JsonProperty public String city;
    @JsonProperty public int resultNumber;

    G2CarSearchRequest(CarSearchRequest csr) {
        this.address = "";
        this.checkin = "";
        this.checkout = "";
        this.city = csr.city;
        this.resultNumber = 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        G2CarSearchRequest that = (G2CarSearchRequest) o;

        if (resultNumber != that.resultNumber) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (checkin != null ? !checkin.equals(that.checkin) : that.checkin != null) return false;
        if (checkout != null ? !checkout.equals(that.checkout) : that.checkout != null) return false;
        return city != null ? city.equals(that.city) : that.city == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (checkin != null ? checkin.hashCode() : 0);
        result = 31 * result + (checkout != null ? checkout.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + resultNumber;
        return result;
    }
}
