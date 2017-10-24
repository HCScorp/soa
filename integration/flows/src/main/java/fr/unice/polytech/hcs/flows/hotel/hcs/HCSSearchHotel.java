package fr.unice.polytech.hcs.flows.hotel.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.SimplePostRoute;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class HCSSearchHotel extends SimplePostRoute<HotelSearchRequest, HotelSearchResponse> {

    public HCSSearchHotel() {
        super(HCS_SEARCH_HOTEL_MQ,
                HCS_SEARCH_HOTEL_EP,
                new JsonDataFormat(JsonLibrary.Jackson),
                HCSHotelSearchRequest::new,
                fsRes -> new ObjectMapper().convertValue(fsRes, HotelSearchResponse.class),
                "hcs-search-hotel-ws",
                "Send the hotel search request to the HCS hotel WS");
    }
}
