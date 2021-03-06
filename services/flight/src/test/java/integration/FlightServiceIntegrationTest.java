package integration;

import flight.service.FlightService;
import org.json.JSONObject;
import org.junit.Ignore;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightServiceIntegrationTest {
    @Ignore
    void filterValueError() {
        JSONObject json = new JSONObject();
        json.put("origin", 80);
        Response result = new FlightService().search(json.toString());
        assertEquals(400, result.getStatus());
    }
}
