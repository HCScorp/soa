package fr.unice.polytech.hcs.flows.travel;

import com.mongodb.DBObject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.HashMap;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchTravel extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
        ;

        rest("/travel")
                .post("/search")
                .type(TravelRequest.class)
                .to(SEARCH_TRAVEL)
        ;

        from(SEARCH_TRAVEL)
                .routeId("search-travel")
                .routeDescription("Retrieve information for a specific travel")

                .log("[" + SEARCH_TRAVEL + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")

                .log("[" + SEARCH_TRAVEL + "] Convert TravelRequest to HashMap")
                .marshal().json(JsonLibrary.Jackson)
                .unmarshal().json(JsonLibrary.Jackson, HashMap.class)

                .inOut(GET_TRAVEL)

                .log("[" + SEARCH_TRAVEL + "] Unmarshalling to TravelResponse")
                .convertBodyTo(HashMap.class)
                .process(e -> {
                    HashMap map = (HashMap) e.getIn().getBody();
                    map.remove("_id");
                    e.getIn().setBody(map);
                })
        ;

        from(GET_TRAVEL)
                .routeId("get-travel")
                .routeDescription("Get travel in database")

                .log("[" + GET_TRAVEL + "] Converting HashMap to DBObject")
                .convertBodyTo(DBObject.class)

                .to(SEARCH_TRAVEL_DATABASE_EP)
        ;

    }
}
