package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
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

                .log("[" + SEARCH_TRAVEL + "] Put header id into body")
                .process(e -> e.getIn().setBody(Collections.singletonMap("travelId", e.getIn().getHeader("travelId", Integer.class))))

                .inOut(GET_TRAVEL)

                .log("[" + SEARCH_TRAVEL + "] Unmarshalling to Travel")
                .process(e -> {
                    Map map = e.getIn().getBody(Map.class);
                    Travel travel = new ObjectMapper().convertValue(map, Travel.class);
                    e.getIn().setBody(new ObjectMapper().convertValue(travel, Map.class));
                })

                .marshal().json(JsonLibrary.Jackson)
        ;

        from(GET_TRAVEL)
                .routeId("get-travel")
                .routeDescription("Get travel in database")

                .log("[" + GET_TRAVEL + "] Converting HashMap to DBObject to request DB")
                .convertBodyTo(DBObject.class)

                .log("[" + GET_TRAVEL + "] Sending request to DB")
                .inOut(SEARCH_TRAVEL_DATABASE_EP)

                .log("[" + GET_TRAVEL + "] Processing response (check for null)")
                .process(e -> {
                    if (e.getIn().getBody() == null) {
                        e.getIn().setBody(Collections.emptyMap());
                    }
                })
        ;

    }
}
