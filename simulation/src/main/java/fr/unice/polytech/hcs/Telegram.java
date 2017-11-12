package fr.unice.polytech.hcs;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.Serializable;

public class Telegram {

    private static final String BUS_URL = "http://localhost:8181/camel/rest";
    private static final String APPROVER_URL = "http://localhost:8080/approver-service-rest/btr";


    public static String on(String s) {
        return BUS_URL + s;
    }

    public static Flight searchFlight(FlightSearchRequest request) throws UnirestException {
        return search("/flight", Flight.class, request).getBody();
    }

    public static Hotel searchHotel(HotelSearchRequest request) throws UnirestException {
        return search("/hotel", Hotel.class, request).getBody();
    }

    public static Car searchCar(CarSearchRequest request) throws UnirestException {
        return search("/car", Car.class, request).getBody();
    }

    public static <T> HttpResponse<T> search(String path, Class<T> outClass, Serializable request) throws UnirestException {
        return Unirest.post(on(path))
                .body(request)
                .asObject(outClass);
    }

    public static String sendBtr(BusinessTravelRequest btr) throws UnirestException {
        HttpResponse<JsonNode> response =
                Unirest.post(APPROVER_URL)
                        .body(btr)
                        .asJson();
        return response.getBody().getObject().getString("_id");
    }

    public static BusinessTravelRequest getBtr(String btrId) throws UnirestException {
        HttpResponse<BusinessTravelRequest> response =
                Unirest.get(APPROVER_URL + "/{id}")
                        .routeParam("id", btrId)
                        .asObject(BusinessTravelRequest.class);
        return response.getBody();
    }

    public static void approveBtr(String btrId) throws UnirestException {
        Unirest.put(APPROVER_URL + "/{id}")
                .routeParam("id", btrId)
                .queryString("status", "APPROVED")
                .asJson();
    }
}
