package scenarios;

import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import com.jcabi.http.response.RestResponse;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.Document;
import scenarios.data.BusinessTravelRequest;
import scenarios.data.BusinessTravelRequestStatus;
import scenarios.data.Flight;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.time.Duration;
import java.time.LocalDate;

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
    public void givenAFlight(String origin, String destination, int price) {
        flight = new Flight(origin, destination, LocalDate.now(), price,
                Flight.JourneyType.DIRECT, Duration.ZERO, Flight.Category.BUSINESS, "Mur Airline");
    }

    @And("^a BTR for this flight$")
    public void aBTRForThisFlight(){
        btr = new BusinessTravelRequest();
        btr.getFlights().add(flight);
        btr.setStatus(BusinessTravelRequestStatus.WAITING);
        btr.setId(1);
    }

    @When("^the btr is submitted$")
    public void theBtrIsSubmitted() throws IOException {
        submit(btr);
    }

    @Then("^the btr is registered$")
    public void theBtrIsRegistered() throws IOException {
        BusinessTravelRequest b  = getBTR(btr.getId());
        assertEquals(b.getId(), b.getId());

    }

    @And("^its status is (.*)$")
    public void itsStatusIsAPPROVED(String status)throws IOException {
        assertEquals(status, getBTR(btr.getId()).getStatus().toString());
    }

    @When("^the btr is approved$")
    public void approveBTR() throws IOException {
        btr.setStatus(BusinessTravelRequestStatus.APPROVED);
        update(btr.getId(), BusinessTravelRequestStatus.APPROVED.toString());
    }

    private BusinessTravelRequest getBTR(int id) throws IOException {
        String resp = new ApacheRequest("http://" + host + ":" + port + "/approver-service-rest/btr/" + id)
                .method(Request.GET).fetch().as(RestResponse.class).assertStatus(HttpURLConnection.HTTP_OK).body();

        return new BusinessTravelRequest(Document.parse(resp));
    }

    private void submit(BusinessTravelRequest btr) throws IOException {
        JsonReader reader = Json.createReader(new StringReader(btr.toJSON().toString()));
        JsonStructure object = reader.readObject();
        reader.close();
        new ApacheRequest("http://" + host + ":" + port + "/approver-service-rest/btr/")
                .method(Request.POST).body().set(object.toString()).back().
                        header("Content-Type", "application/json").fetch();

    }

    private void update(int id, String status) throws IOException {
        new ApacheRequest("http://" + host + ":" + port + "/approver-service-rest/btr/" + id)
                .uri()
                .queryParam("status", status)
                .back()
                .method(Request.PUT)
                .fetch();
    }
}
