package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.bson.types.ObjectId;

import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class TravelDatabase extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from(GET_TRAVEL_DB_OBJECT)
                .routeId("get-travel-db-object")
                .routeDescription("Get travel in database")

                .log("[" + GET_TRAVEL_DB_OBJECT + "] Translating TravelRequest to Map")
                .log("[" + GET_TRAVEL_DB_OBJECT + "] IN: ${body}")
                .process(e -> {
                    TravelRequest travelRequest = e.getIn().getBody(TravelRequest.class);
                    e.getIn().setBody(new ObjectMapper().convertValue(travelRequest, Map.class));
                })
                .log("[" + GET_TRAVEL_DB_OBJECT + "] OUT: ${body}")

                .log("[" + GET_TRAVEL_DB_OBJECT + "] Sending request to DB")
                .inOut(SEARCH_TRAVEL_DATABASE_EP)
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
                    map.put("_id", new ObjectId(travel.travelId));

                    e.getIn().setBody(map);
                })

                .log("[" + UPDATE_TRAVEL + "] Update database")
                .to(SAVE_TRAVEL_DATABASE_EP)

                .log("[" + UPDATE_TRAVEL + "] Restore travel")
                .setBody(header("travel"))
        ;
    }
}
