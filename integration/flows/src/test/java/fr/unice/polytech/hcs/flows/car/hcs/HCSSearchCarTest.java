package fr.unice.polytech.hcs.flows.car.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.SpecificSearchTest;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;
import fr.unice.polytech.hcs.flows.car.CarSearchResponse;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_CAR_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_CAR_MQ;

public class HCSSearchCarTest extends SpecificSearchTest {

    public HCSSearchCarTest() {
        super(HCS_SEARCH_CAR_EP, HCS_SEARCH_CAR_MQ, new HCSSearchCar());
    }

    private final String csResJson = "{" +
            "  \"result\": [" +
            "    {" +
            "      \"company\": \"Tesla location\"," +
            "      \"city\": \"Nice\"," +
            "      \"model\": \"S P100D\"," +
            "      \"price\": 35," +
            "      \"numberPlate\": \"888-999\"" +
            "    }," +
            "    {" +
            "      \"company\": \"Mercedes location\"," +
            "      \"city\": \"Nice\"," +
            "      \"model\": \"Class A\"," +
            "      \"price\": 40," +
            "      \"numberPlate\": \"555-444\"" +
            "    }" +
            "  ]" +
            "}";

    @Override
    public void initVariables() throws Exception {
        CarSearchRequest csr = new CarSearchRequest();
        csr.city = "Nice";
        csr.dateFrom = "2017-12-21";
        csr.dateTo = "2017-12-25";

        this.genericRequest = csr;
        this.genericResponse = new ObjectMapper().readValue(csResJson, CarSearchResponse.class);
        this.specificResultJson = csResJson;
    }
}
