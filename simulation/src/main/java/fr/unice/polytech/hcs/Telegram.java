package fr.unice.polytech.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import fr.unice.polytech.hcs.pojo.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Telegram {

    private static final String BUS_URL = "http://localhost:8181/camel/rest";
    private static final String APPROVER_URL = "http://localhost:9090/approver-service-rest/btr";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void waitForBus() throws InterruptedException {
        boolean connected = false;
        while(!connected) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("localhost", 8181), 1000);
                connected = true;
            } catch (IOException e) {
                connected =  false;
            }
        }
        Thread.sleep(2000);
    }

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
        HttpResponse<BtrResponse> response =
                Unirest.post(APPROVER_URL)
                        .body(btr)
                        .asObject(BtrResponse.class);
        return response.getBody()._id;
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
                .asBinary();
    }

    public static void sendExpenseReport(ExpenseReport e) throws IOException {
        MAPPER.writeValue(new File("/email/" + e.travelId + ".json"), e);
    }

    public static Travel getTravel(String btrId) throws UnirestException {
        HttpResponse<Travel> response =
                Unirest.get(on("/travel/{id}"))
                        .routeParam("id", btrId)
                        .asObject(Travel.class);
        return response.getBody();
    }

    public static RefundStatus endTravel(String btrId) throws UnirestException {
        HttpResponse<RefundStatus> response = Unirest.put(on("/travel/{id}"))
                .routeParam("id", btrId)
                .asObject(RefundStatus.class);
        return response.getBody();
    }

    public static void sendExplanation(Explanation e) throws UnirestException {
        Unirest.post(on("/explanation"))
                .body(e)
                .asBinary();
    }

    public static void sendExplanationAnswer(ExplanationAnswer e) throws UnirestException {
        Unirest.put(on("/explanation"))
                .body(e)
                .asBinary();
    }
}
