package integration;

import car.service.CarService;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarServiceIntegrationTest {
    @Ignore
    void filterValueError() {
        JSONObject json = new JSONObject();
        json.put("city", 80);
        Response result = new CarService().search(json.toString());
        assertEquals(400, result.getStatus());
    }
}
