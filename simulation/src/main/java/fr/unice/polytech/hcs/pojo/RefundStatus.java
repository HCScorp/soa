package fr.unice.polytech.hcs.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RefundStatus implements Serializable {

    @JsonProperty public String status;
    @JsonProperty public String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundStatus that = (RefundStatus) o;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RefundStatus{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
