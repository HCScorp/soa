package approver.service;

import approver.data.BusinessTravelRequest;
import approver.data.Car;
import approver.data.Flight;
import approver.data.Hotel;
import approver.data.database.BTRHandler;
import approver.data.database.Network;
import approver.data.database.exception.BTRNotFound;
import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApproverServiceTest {

    private ApproverService service;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    private BTRHandler btrHandler;

    @BeforeEach
    public void setUp() {
        Fongo f = new Fongo("Business Travel Request DB");
        db = f.getDatabase(Network.DATABASE);
        collection = db.getCollection(Network.COLLECTION);
        btrHandler = new BTRHandler(collection);
        service = new ApproverService(btrHandler);
    }

    @AfterEach
    public void cleanUp() {
        collection.drop();
    }

    private BusinessTravelRequest generate() {
        BusinessTravelRequest btr = new BusinessTravelRequest();

        btr.setId(new ObjectId());
        btr.getCars().add(new Car("Tesla", "Nuou-Yaurque", "Model XXL", "AL-666-HELL", 100.5));
        btr.getFlights().add(new Flight("LAX", "NYC", LocalDate.now(), LocalTime.now(),
                100.5, Flight.JourneyType.DIRECT, Duration.ofHours(1), Flight.Category.BUSINESS, "Air Mur"));
        btr.getHotels().add(new Hotel("Hotel #1", "Nuou-Yaurque", "2", "z", 25.5));

        return btr;
    }

    @Test
    public void insertTest() throws BTRNotFound {
        BusinessTravelRequest oracle = generate();

        Response res = service.createBTR(oracle.toBSON().toJson());
        oracle.setId(new ObjectId(new JSONObject(res.getEntity().toString()).getString("_id")));

        BusinessTravelRequest insertedBTR = btrHandler.getBTR(oracle.getId());

        assertEquals(oracle, insertedBTR);
    }

    @Test
    public void updateTest() throws BTRNotFound {
        BusinessTravelRequest oracle = generate();
        btrHandler.insert(oracle);

        service.updateBTR(oracle.getId().toHexString(), "APPROVE");
        oracle.setStatus(BusinessTravelRequest.Status.APPROVED);

        assertEquals(oracle, btrHandler.getBTR(oracle.getId()));
    }

    @Test
    public void getTest() {
        BusinessTravelRequest oracle = generate();
        btrHandler.insert(oracle);

        Response res = service.getBTR(oracle.getId().toHexString());
        Document result = Document.parse(res.getEntity().toString());
        BusinessTravelRequest btr = new BusinessTravelRequest(result);

        assertEquals(oracle, btr);
    }
}
