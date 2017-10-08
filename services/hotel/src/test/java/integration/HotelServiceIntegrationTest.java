package integration;

import hotel.service.HotelService;
import org.json.JSONObject;
import org.junit.Ignore;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelServiceIntegrationTest {
    @Ignore
    void filterValueError() {
        JSONObject json = new JSONObject();
        json.put("destination", 80);
        Response result = new HotelService().search(json.toString());
        assertEquals(400, result.getStatus());
    }
}
