package fr.unice.polytech.hcs.flows.flight.g1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.SpecificSearchTest;
import fr.unice.polytech.hcs.flows.flight.FlightSearchRequest;
import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class G1SearchFlightTest extends SpecificSearchTest {

    public G1SearchFlightTest() {
        super(G1_SEARCH_FLIGHT_EP, G1_SEARCH_FLIGHT_MQ, new G1SearchFlight());
    }

    private final String fsResJson = "{\n" +
            "    \"flights\": [\n" +
            "        {\n" +
            "            \"departure\": 710171030,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 66,\n" +
            "            \"arrival\": 710171730,\n" +
            "            \"seatClass\": \"Green\",\n" +
            "            \"price\": 1188.39,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 700\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710171130,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 49,\n" +
            "            \"arrival\": 710171630,\n" +
            "            \"seatClass\": \"Aquamarine\",\n" +
            "            \"price\": 1662.52,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 500\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710170930,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 91,\n" +
            "            \"arrival\": 710171530,\n" +
            "            \"seatClass\": \"Orange\",\n" +
            "            \"price\": 1449.74,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 600\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710171530,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 20,\n" +
            "            \"arrival\": 710172030,\n" +
            "            \"seatClass\": \"Maroon\",\n" +
            "            \"price\": 537.02,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 500\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710170930,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 81,\n" +
            "            \"arrival\": 710171530,\n" +
            "            \"seatClass\": \"Purple\",\n" +
            "            \"price\": 1554.62,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 600\n" +
            "        },\n" +
            "        {\n" +
            "            \"departure\": 710171730,\n" +
            "            \"numberOfFlights\": 1,\n" +
            "            \"ticketNo\": 76,\n" +
            "            \"arrival\": 710172230,\n" +
            "            \"seatClass\": \"Orange\",\n" +
            "            \"price\": 1326.6,\n" +
            "            \"_id\": null,\n" +
            "            \"from\": \"Paris\",\n" +
            "            \"to\": \"New-York\",\n" +
            "            \"duration\": 500\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Override
    public void initVariables() throws Exception {
        FlightSearchRequest fsr = new FlightSearchRequest();
        fsr.origin = "Nice";
        fsr.destination = "Paris";
        fsr.date = "2017-01-05";
        fsr.airline = "Air France";
        fsr.category = "ECO";
        fsr.journeyType = "DIRECT";
        fsr.order = "ASCENDING";
        fsr.timeFrom = "08:00:00" ;
        fsr.timeTo = "14:30:00";
        fsr.maxTravelTime = 200;

        this.genericRequest = fsr;
        this.specificRequest = new G1FlightSearchRequest(fsr);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> fsResMap = mapper.readValue(fsResJson, typeRef);
        this.genericResponse = G1SearchFlight.mapToFsRes(fsResMap);
        this.specificResultJson = fsResJson;
    }
}
