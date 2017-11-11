package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Collections;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class SearchTravel extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
        ;

        rest("/travel")
                .get("/{travelId}")
                .to(SEARCH_TRAVEL)
        ;

        from(SEARCH_TRAVEL)
                .routeId("search-travel")
                .routeDescription("Retrieve information for a specific travel")

                .log("[" + SEARCH_TRAVEL + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")

                .log("[" + SEARCH_TRAVEL + "] Translating JSON to Travel Request")
                .process(e -> {
                    TravelRequest travelRequest = new TravelRequest();
                    travelRequest.travelId = e.getIn().getHeader("travelId", Integer.class);

                    e.getIn().setBody(travelRequest);
                })

                .inOut(GET_TRAVEL)

                .log("[" + SEARCH_TRAVEL + "] Marshalling to JSON")
                .process(e -> {
                    Travel travel = e.getIn().getBody(Travel.class);
                    e.getIn().setBody(new ObjectMapper().convertValue(travel, Map.class));
                })
                .marshal().json(JsonLibrary.Jackson)
        ;

        from(GET_TRAVEL)
                .routeId("get-travel")
                .routeDescription("Get travel object")

                .inOut(GET_TRAVEL_DB_OBJECT)

                .log("[" + SEARCH_TRAVEL + "] Unmarshalling to Travel")
                .process(e -> {
                    Map map = e.getIn().getBody(Map.class);
                    Travel travel = new ObjectMapper().convertValue(map, Travel.class);
                    e.getIn().setBody(travel);
                })
        ;

        from(GET_TRAVEL_DB_OBJECT)
                .routeId("get-travel-db-object")
                .routeDescription("Get travel in database")

                .log("[" + GET_TRAVEL_DB_OBJECT + "] Translating Travel Request to DBObject")
                .process(e -> {
                    TravelRequest travelRequest = e.getIn().getBody(TravelRequest.class);
                    e.getIn().setBody(new ObjectMapper().convertValue(travelRequest, Map.class));
                })

                .log("[" + GET_TRAVEL_DB_OBJECT + "] Sending request to DB")
                .inOut(SEARCH_TRAVEL_DATABASE_EP)

                .log("[" + GET_TRAVEL_DB_OBJECT + "] Processing response (check for null)")
                .process(e -> {
                    if (e.getIn().getBody() == null) {
                        e.getIn().setBody(Collections.emptyMap());
                    }
                })
        ;

    }
}
