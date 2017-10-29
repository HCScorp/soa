package fr.unice.polytech.hcs.flows.car.hcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;
import fr.unice.polytech.hcs.flows.car.CarSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.SimplePostRoute;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class HCSSearchCar extends SimplePostRoute<CarSearchRequest, CarSearchResponse> {

    public HCSSearchCar() {
        super(HCS_SEARCH_CAR_MQ,
                HCS_SEARCH_CAR_EP,
                new JsonDataFormat(JsonLibrary.Jackson),
                HCSCarSearchRequest::new,
                csRes -> new ObjectMapper().convertValue(csRes, CarSearchResponse.class),
                "hcs-search-car-ws",
                "Send the car search request to the HCS car WS");
    }
}
