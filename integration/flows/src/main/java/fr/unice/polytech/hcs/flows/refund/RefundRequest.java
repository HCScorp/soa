package fr.unice.polytech.hcs.flows.refund;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RefundRequest implements Serializable {
    @JsonProperty public String city;
    @JsonProperty public String date;
    @JsonProperty public int amount;
    @JsonProperty public String reason;
    @JsonProperty public String name;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundRequest that = (RefundRequest) o;

        if (amount != that.amount) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
