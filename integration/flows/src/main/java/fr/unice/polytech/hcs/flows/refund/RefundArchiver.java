package fr.unice.polytech.hcs.flows.refund;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_SENDING;

public class RefundArchiver extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        // In progress
        from(REFUND_SENDING)
                .routeDescription("where to send your refund piece")
                .routeId("refund-piece-route")

                .process(exchange -> {
                    Travel travel =  exchange.getIn().getBody(Travel.class);

                    exchange.getIn().setHeader("id", Integer.toString(travel.travelId));
                    exchange.getIn().setBody(new ObjectMapper().convertValue(travel, Map.class));
                })
                .marshal().json(JsonLibrary.Jackson)
                .toD("ftp://ftp-server:21/${header.id}?username=test&password=test")
                .log("file sended to the ftp server !");
    }
}
