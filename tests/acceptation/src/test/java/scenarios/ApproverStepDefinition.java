package scenarios;

import approver.data.BusinessTravelRequest;
import approver.data.Flight;
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.ApacheRequest;
import com.jcabi.http.response.RestResponse;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class ApproverStepDefinition {
    private int port;
    private String host;

    private Flight flight;
    private BusinessTravelRequest btr;

    @Given("^The Approver service deployed on (.*):(\\d+)$")
    public void theApproverServiceDeployed(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a flight from (.*) to (.*) for today at (\\d+)€$")
    public void givenAFlight(String origin, String destination, double price) {
        flight = new Flight(origin, destination, LocalDate.now(), LocalTime.now(), price,
                Flight.JourneyType.DIRECT, Duration.ofMinutes(90), Flight.Category.BUSINESS, "Mur Airline");
    }

    @And("^a BTR for this flight$")
    public void aBTRForThisFlight(){
        btr = new BusinessTravelRequest();
        btr.getFlights().add(flight);
    }

    @When("^the btr is submitted$")
    public void theBtrIsSubmitted() throws IOException {
        btr.setId(submit(btr));
    }

    @Then("^the btr is registered$")
    public void theBtrIsRegistered() throws IOException {
        BusinessTravelRequest b  = getBTR(btr.getId());
        assertEquals(btr.getId(), b.getId());

    }

    @And("^its status is (.*)$")
    public void itsStatusIsAPPROVED(String status)throws IOException {
        assertEquals(status, getBTR(btr.getId()).getStatus().toString());
    }

    @When("^the btr is approved$")
    public void approveBTR() throws IOException {
        btr.setStatus(BusinessTravelRequest.Status.APPROVED);
        update(btr.getId(), "APPROVE");
    }

    private BusinessTravelRequest getBTR(ObjectId id) throws IOException {
        String resp = new ApacheRequest("http://" + host + ":" + port + "/approver-service-rest/btr/" + id.toHexString())
                .method(Request.GET).fetch().as(RestResponse.class).assertStatus(HttpURLConnection.HTTP_OK).body();

        return new BusinessTravelRequest(Document.parse(resp));
    }

    private ObjectId submit(BusinessTravelRequest btr) throws IOException {
        JsonReader reader = Json.createReader(new StringReader(btr.toBSON().toJson()));
        JsonStructure object = reader.readObject();
        reader.close();
        Response req = new ApacheRequest("http://" + host + ":" + port + "/approver-service-rest/btr/")
                .method(Request.POST).body().set(object.toString()).back().
                        header("Content-Type", "application/json").fetch();
        return new ObjectId(new JSONObject(req.body()).getString("_id"));
    }

    private void update(ObjectId id, String action) throws IOException {
        new ApacheRequest("http://" + host + ":" + port + "/approver-service-rest/btr/" + id.toHexString())
                .uri()
                .queryParam("action", action)
                .back()
                .method(Request.PUT)
                .fetch();
    }
}
