package fr.unice.polytech.hcs.flows.car.g2;


import fr.unice.polytech.hcs.flows.car.Car;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;
import fr.unice.polytech.hcs.flows.car.CarSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.SimpleGetRoute;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Collection;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G2_SEARCH_CAR_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G2_SEARCH_CAR_MQ;

public class G2SearchCar extends SimpleGetRoute<CarSearchRequest, CarSearchResponse> {

    // TODO dynamic get url

    public G2SearchCar() {
        super(G2_SEARCH_CAR_MQ,
                G2_SEARCH_CAR_EP,
                new JsonDataFormat(JsonLibrary.Jackson),
                G2CarSearchRequest::new,
                G2SearchCar::mapToCsRes,
                "g2-search-car-ws",
                "Send the car search request to the G2 car WS");
    }

    public static CarSearchResponse mapToCsRes(Map<String, Object> map) {
        Collection<Map<String, Object>> cars = (Collection<Map<String, Object>>) map.get("cars");

        CarSearchResponse csr = new CarSearchResponse();
        csr.result = new Car[cars.size()];
        int i = 0;
        for (Map<String, Object> m : cars) {
            Car c = new Car();
//            c.origin = (String) m.get("from");
//            f.destination = (String) m.get("to");
//            Date departure = new Date((Integer) m.get("departure"));
//            LocalDateTime dateTime = LocalDateTime.ofInstant(departure.toInstant(), ZoneId.systemDefault());
//            f.date = dateTime.toLocalDate().toString();
//            f.time = dateTime.toLocalTime().toString();
//            f.price = Cast.toDouble(m.get("price"));
//            f.duration = (Integer) m.get("duration");
//            f.category = (String) m.get("seatClass");
//            f.airline = (String) m.get("airline");
            csr.result[i++] = c;
        }

        return csr;
    }
}
