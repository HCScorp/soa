package approver.service;

import approver.data.*;
import approver.data.database.BTRHandler;
import approver.data.database.DB;
import approver.data.database.exception.BTRNotFound;
import com.github.fakemongo.Fongo;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApproverServiceTest {

    private ApproverService service;

    @BeforeAll
    public static void initialize(){
        Fongo f = new Fongo("btr");
        BTRHandler.db = new DB(f.getDatabase("btr"));
    }

    @BeforeEach
    public void setUp(){
        service = new ApproverService();
    }

    @AfterEach
    public void cleanUp(){
        BTRHandler.db.getBTR().drop();
    }

    private BusinessTravelRequest generate(){
        BusinessTravelRequest btr = new BusinessTravelRequest();
        btr.setId(1);
        btr.getCars().add(new Car("Tesla", "Nuou-Yaurque", "Model XXL", "AL-666-HELL", new ArrayList<>()));
        btr.getFlights().add(new Flight("LAX", "NYC", LocalDate.now(),
                100, Flight.JourneyType.DIRECT, Duration.ZERO, Flight.Category.BUSINESS, "Air Mur"));
        btr.getHotels().add(new Hotel("Hotel #1", "Nuou-Yaurque", "2", "z", 1, new ArrayList<>()));

        return btr;
    }

    @Test
    public void insertTest() throws BTRNotFound {
        BusinessTravelRequest oracle = generate();

        service.createNewBTR(oracle.toJSON().toString());

        BusinessTravelRequest insertedBTR = BTRHandler.get(oracle.getId());

        assertEquals(oracle, insertedBTR);
    }

    @Test
    public void updateTest() throws BTRNotFound {
        BusinessTravelRequest oracle = generate();
        BTRHandler.insert(oracle.toBSON());

        service.changeStatus(oracle.getId(), BusinessTravelRequestStatus.APPROVED.toString());
        oracle.setStatus(BusinessTravelRequestStatus.APPROVED);

        assertEquals(oracle, BTRHandler.get(oracle.getId()));
    }

    @Test
    public void getTest(){
        BusinessTravelRequest oracle = generate();
        BTRHandler.insert(oracle.toBSON());

        Document result = Document.parse(service.getBTR(oracle.getId()).getEntity().toString());
        BusinessTravelRequest btr = new BusinessTravelRequest(result);

        assertEquals(oracle, btr);
    }
}
