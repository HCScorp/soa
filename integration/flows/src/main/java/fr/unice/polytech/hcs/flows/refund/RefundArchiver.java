package fr.unice.polytech.hcs.flows.refund;

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
                .marshal().json(JsonLibrary.Jackson)
                .toD("ftp://localhost:11021/out?username=test&password=test&passiveMode=true&fileName=out.json");
    }
}
