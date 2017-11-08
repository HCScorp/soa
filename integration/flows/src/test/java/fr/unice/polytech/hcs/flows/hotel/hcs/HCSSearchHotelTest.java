package fr.unice.polytech.hcs.flows.hotel.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.SpecificSearchTest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchResponse;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_HOTEL_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.HCS_SEARCH_HOTEL_MQ;

public class HCSSearchHotelTest extends SpecificSearchTest {

    public HCSSearchHotelTest() {
        super(HCS_SEARCH_HOTEL_EP, HCS_SEARCH_HOTEL_MQ, new HCSSearchHotel());
    }

    private final String hsResJson = "{" +
            "  \"result\": [" +
            "  {" +
            "      \"name\": \"Le Méridien\"," +
            "      \"city\": \"Nice\"," +
            "      \"zipCode\": \"06046\"," +
            "      \"address\": \"1, promenade des Anglais\"," +
            "      \"nightPrice\": 200.0" +
            "  }," +
            "  {" +
            "      \"name\": \"Négresco\"," +
            "      \"city\": \"Nice\"," +
            "      \"zipCode\": \"06000\"," +
            "      \"address\": \"37, promenade des Anglais\"," +
            "      \"nightPrice\": 300.0" +
            "  }" +
            " ]" +
            "}";

    @Override
    public void initVariables() throws Exception {
        HotelSearchRequest hsr = new HotelSearchRequest();
        hsr.city = "Paris";
        hsr.dateFrom = "2017-10-21";
        hsr.dateTo = "2017-10-26";
        hsr.order = "ASCENDING";

        this.genericRequest = hsr;
        this.genericResponse = new ObjectMapper().readValue(hsResJson, HotelSearchResponse.class);
        this.specificResultJson = hsResJson;
    }
}
