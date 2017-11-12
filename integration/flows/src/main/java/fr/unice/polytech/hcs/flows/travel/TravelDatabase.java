package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;

import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class TravelDatabase extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from(GET_TRAVEL)
                .routeId("get-travel")
                .routeDescription("Get travel object")

                .log("[" + GET_TRAVEL + "] Translating TravelRequest to Map")
                .log("[" + GET_TRAVEL + "] IN: ${body}")
                .process(e -> {
                    TravelRequest travelRequest = e.getIn().getBody(TravelRequest.class);
                    e.getIn().setBody(new ObjectMapper().convertValue(travelRequest, Map.class));
                })
                .log("[" + GET_TRAVEL + "] OUT: ${body}")

                .log("[" + GET_TRAVEL + "] Sending request to DB")
                .inOut(SEARCH_TRAVEL_DATABASE_EP)

                .log("[" + SEARCH_TRAVEL + "] Unmarshalling to Travel")
                .process(e -> {
                    Map map = e.getIn().getBody(Map.class);
                    Travel travel = new ObjectMapper().convertValue(map, Travel.class);
                    e.getIn().setBody(travel);
                })
        ;

        from(UPDATE_TRAVEL)
                .routeId("update-travel")
                .routeDescription("Update travel in database from Travel object")

                .log("[" + UPDATE_TRAVEL + "] Backup travel")
                .setHeader("travel", body())

                .log("[" + UPDATE_TRAVEL + "] Convert Travel to DBObject")
                .process(e -> {
                    Travel travel = e.getIn().getBody(Travel.class);

                    Map map = new ObjectMapper().convertValue(travel, Map.class);
                    map.put("_id", travel.travelId);

                    e.getIn().setBody(map);
                })

                .log("[" + UPDATE_TRAVEL + "] Update database")
                .to(SAVE_TRAVEL_DATABASE_EP)

                .log("[" + UPDATE_TRAVEL + "] Restore travel")
                .setBody(header("travel"))
        ;
    }
}
