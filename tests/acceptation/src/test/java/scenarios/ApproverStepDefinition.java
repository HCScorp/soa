package scenarios;

import approver.data.BusinessTravelRequest;
import approver.data.BusinessTravelRequestStatus;
import approver.data.Flight;
import approver.data.database.BTRHandler;
import approver.data.database.DB;
import approver.data.database.exception.BTRNotFound;
import approver.service.ApproverService;
import com.github.fakemongo.Fongo;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ApproverStepDefinition {
    private ApproverService service;
    private Flight flight;
    private BusinessTravelRequest btr;

    @Given("^The Approver service deployed$")
    public void theApproverServiceDeployed() {
        Fongo f = new Fongo("btr");
        BTRHandler.db = new DB(f.getDatabase("btr"));
        service = new ApproverService();
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
        btr.setId(1);
    }

    @When("^the btr is submitted$")
    public void theBtrIsSubmitted() {
        service.createNewBTR(btr.toJSON().toString());
    }

    @Then("^the btr is registered$")
    public void theBtrIsRegistered() throws BTRNotFound {
        assertEquals(1, BTRHandler.db.getBTR().count());
        assertEquals(btr, BTRHandler.get(btr.getId()));

    }

    @And("^its status is (.*)$")
    public void itsStatusIsAPPROVED(String status) throws BTRNotFound {
        assertEquals(status, BTRHandler.get(btr.getId()).getStatus().toString());
    }


    @When("^the btr is approved$")
    public void approveBTR() {
        service.changeStatus(btr.getId(), BusinessTravelRequestStatus.APPROVED.toString());
    }
}
