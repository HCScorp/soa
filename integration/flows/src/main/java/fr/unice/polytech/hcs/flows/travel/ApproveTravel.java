package fr.unice.polytech.hcs.flows.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.expense.Travel;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ApproveTravel extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
        ;

        rest("/travel").consumes("application/json").produces("application/json")
                .get("/{travelId}/done")
                .to(APPROVE_TRAVEL)
        ;

        from(APPROVE_TRAVEL)
                .routeId("approve-travel")
                .routeDescription("End a travel and check refund")

                .log("[" + SEARCH_TRAVEL + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")

                .log("[" + SEARCH_TRAVEL + "] Put header id into body")
                .process(e -> e.getIn().setBody(Collections.singletonMap("travelId", e.getIn().getHeader("travelId", Integer.class))))

                .log("[" + APPROVE_TRAVEL + "] Load travel")
                .inOut(Endpoints.SEARCH_TRAVEL)

                .log("[" + APPROVE_TRAVEL + "] Sum expenses")
                .process(sumExpensesTravel)

                .log("[" + APPROVE_TRAVEL + "] Check automatic refund")
                .process(checkAutomaticRefund)

                .log("[" + ACCEPT_REFUND + "] Extract travel")
                .process(e -> {
                    Approval approval = e.getIn().getBody(Approval.class);
                    e.getIn().setBody(approval.travel);
                })

                .choice()
                    .when(simple("${header.autoRefund} == true"))
                        .log("[" + APPROVE_TRAVEL + "] Automatic refund")
                        .inOut(ACCEPT_REFUND)
                    .otherwise()
                        .log("[" + APPROVE_TRAVEL + "] Manual refund")
                        .inOut(MANUAL_REFUND)
                .end()

                .marshal().json(JsonLibrary.Jackson)
        ;

        from(ACCEPT_REFUND)
                .routeId("automatic-refund")
                .routeDescription("Automatic refund")

                .log("[" + ACCEPT_REFUND + "] Update travel status to Done")
                .process(e -> {
                    Travel travel = e.getIn().getBody(Travel.class);
                    travel.status = "Done";
                    e.getIn().setBody(travel);
                })

                .log("[" + ACCEPT_REFUND + "] Save travel")
                .to(UPDATE_TRAVEL)

                .log("OUT : ${body}")

                .log("[" + ACCEPT_REFUND + "] Refund accepted")
                .to(Endpoints.REFUND_SENDING)

                .process(e -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "Done");
                    response.put("message", "Refund accepted.");

                    e.getIn().setBody(response);
                })
        ;

        from(MANUAL_REFUND)
                .routeId("manual-refund")
                .routeDescription("Manual refund")

                .log("[" + ACCEPT_REFUND + "] Update travel status to Done")
                .process(e -> {
                    Travel travel = e.getIn().getBody(Travel.class);
                    travel.status = "";
                    e.getIn().setBody(travel);
                })

                .log("[" + ACCEPT_REFUND + "] Save travel")
                .to(UPDATE_TRAVEL)

                .process(e -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "Pending");
                    response.put("message", "You need to justify your budget overrun.");

                    e.getIn().setBody(response);
                })
        ;

        from(UPDATE_TRAVEL)
                .routeId("update-travel")
                .routeDescription("Update travel in database")

                .log("[" + UPDATE_TRAVEL + "] Backup new travel")
                .process(e -> e.getIn().setHeader("travel", e.getIn().getBody()))

                .log("[" + UPDATE_TRAVEL + "] Put travel id into body")
                .process(e -> {
                    Travel travel = e.getIn().getHeader("travel", Travel.class);
                    e.getIn().setBody(Collections.singletonMap("travelId", travel.travelId));
                })

                .log("[" + UPDATE_TRAVEL + "] Load database travel")
                .inOut(GET_TRAVEL)

                .process(e -> {
                    Map db = e.getIn().getBody(Map.class);
                    Travel travel = e.getIn().getHeader("travel", Travel.class);

                    // merging travel into db object
                    Map travel_db = new ObjectMapper().convertValue(travel, Map.class);
                    travel_db.put("_id", db.get("_id"));

                    e.getIn().setBody(travel_db);
                })

                .to(Endpoints.SAVE_TRAVEL_DATABASE_EP)
                .process(e -> {
                    Travel travel = e.getIn().getHeader("travel", Travel.class);
                    e.getIn().setBody(travel);
                })
        ;
    }

    private static Processor sumExpensesTravel = (exchange -> {
        Travel travel = new ObjectMapper().readValue(exchange.getIn().getBody(byte[].class), Travel.class);

        // compute sum of expenses
        int sum = travel.documents.stream().mapToInt(expense -> expense.price).sum();

        // create approval object
        Approval approval = new Approval();
        approval.travel = travel;
        approval.sum = sum;

        exchange.getIn().setBody(approval);
    });

    private static Processor checkAutomaticRefund = (exchange -> {
        Approval approval = exchange.getIn().getBody(Approval.class);

        // default refuse automatic refund
        manualRefund(exchange);

        if (approval.sum < 20) {
            automaticRefund(exchange);
        }
    });

    private static void automaticRefund(Exchange exchange) {
        exchange.getIn().setHeader("autoRefund", true);
    }

    private static void manualRefund(Exchange exchange) {
        exchange.getIn().setHeader("autoRefund", false);
    }
}
