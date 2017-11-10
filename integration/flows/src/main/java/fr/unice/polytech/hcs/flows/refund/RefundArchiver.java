package fr.unice.polytech.hcs.flows.refund;

import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_PIECE_OUTPUT_DIR;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_SENDING;

public class RefundArchiver extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        // In progress
        from(REFUND_SENDING)
                .routeDescription("where to send your refund piece")
                .routeId("refund-piece-route")
                .process(exchange -> {

                    Travel travel = exchange.getIn().getBody(Travel.class);
                    exchange.getIn().setHeader("id", Integer.toString(travel.travelId));
                })
                .marshal().json(JsonLibrary.Jackson)
                .toD("ftp://ftp-server:11021/${header.id}?username=test&password=test&passiveMode=true");
    }
}
