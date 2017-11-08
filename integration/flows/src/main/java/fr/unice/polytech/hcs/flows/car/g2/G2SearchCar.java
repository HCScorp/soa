package fr.unice.polytech.hcs.flows.car.g2;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.car.Car;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;
import fr.unice.polytech.hcs.flows.car.CarSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.SimpleJsonGetRoute;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G2_SEARCH_CAR_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G2_SEARCH_CAR_MQ;

public class G2SearchCar extends SimpleJsonGetRoute<CarSearchRequest, CarSearchResponse> {

    public G2SearchCar() {
        super(G2_SEARCH_CAR_MQ,
                G2_SEARCH_CAR_EP,
                G2CarSearchRequest::new,
                G2SearchCar::mapToCsRes,
                G2SearchCar::buildUrl,
                "g2-search-car-ws",
                "Send the car search request to the G2 car WS");
    }

    public static String buildUrl(Object o) {
        G2CarSearchRequest g2Csr = (G2CarSearchRequest) o;
        return "/" + g2Csr.destination + "/" + g2Csr.date + "/" + g2Csr.duration;
    }

    public static CarSearchResponse mapToCsRes(InputStream is) {
        CarSearchResponse csr = new CarSearchResponse();
        csr.result = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<G2Car> cars = mapper.readValue(
                    is,
                    mapper.getTypeFactory().constructCollectionType(List.class, G2Car.class));
            cars.forEach(g2c -> {
                Car c = new Car();
                c.company = g2c.name;
                c.price = g2c.price;
                c.model = "?";
                c.city = "?";
                c.numberPlate = "?";
                csr.result.add(c);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csr;
    }
}
