package fr.unice.polytech.hcs.flows.car.g2;

import fr.unice.polytech.hcs.flows.SpecificSearchTest;
import fr.unice.polytech.hcs.flows.car.CarSearchRequest;
import org.apache.camel.builder.RouteBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G2_SEARCH_CAR_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G2_SEARCH_CAR_MQ;

public class G2SearchCarTest {} // extends SpecificSearchTest {
//
//    public G2SearchCarTest() {
//        super(G2_SEARCH_CAR_EP, G2_SEARCH_CAR_MQ, new G2SearchCar());
//    }
//
//    private final String csResJson = "[" +
//            "    {" +
//            "        \"price\": 48," +
//            "        \"name\": \"Mercedes\"," +
//            "        \"id\": 0" +
//            "    }," +
//            "    {" +
//            "        \"price\": 41," +
//            "        \"name\": \"Mega Loueur\"," +
//            "        \"id\": 1" +
//            "    }" +
//            "]";
//
//    @Override
//    public void initVariables() throws Exception {
//        context.addRoutes(new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                interceptSendToEndpoint("http*").skipSendToOriginalEndpoint()
//                        .to(G2_SEARCH_CAR_EP);
//            }
//        });
//        context.start();
//
//        CarSearchRequest csr = new CarSearchRequest();
//        csr.city = "Nice";
//        csr.dateFrom = "2017-12-21";
//        csr.dateTo = "2017-12-25";
//
//        this.genericRequest = csr;
//
//        InputStream stream = new ByteArrayInputStream(csResJson.getBytes(StandardCharsets.UTF_8.name()));
//        this.genericResponse = G2SearchCar.mapToCsRes(stream);
//
//        this.specificResultJson = csResJson;
//    }
//}