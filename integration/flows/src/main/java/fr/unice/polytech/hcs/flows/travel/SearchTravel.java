package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.SEARCH_TRAVEL;

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
                .outType(TravelResponse.class)
                .to(SEARCH_TRAVEL)
        ;

        from(SEARCH_TRAVEL)
                .routeId("search-travel")
                .routeDescription("Retrieve information for a specific travel")

                .log("[" + SEARCH_TRAVEL + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")

                .log("[" + SEARCH_TRAVEL + "] Unmarshalling body to HashMap")
                .marshal().json(JsonLibrary.Jackson)
                .unmarshal().json(JsonLibrary.Jackson, HashMap.class)

                .log("[" + SEARCH_TRAVEL + "] Converting HashMap to DBObject")
                .convertBodyTo(DBObject.class)
                .log("REQUEST : ${body}")

                .to(Endpoints.SEARCH_TRAVEL_DATABASE)

                .log("[" + SEARCH_TRAVEL + "] Unmarshalling to TravelResponse")
                .unmarshal().json(JsonLibrary.Jackson, TravelResponse.class)
        ;

    }
}
