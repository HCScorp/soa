package fr.unice.polytech.hcs.flows.refund;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_PIECE_OUTPUT_DIR;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_SENDING;

public class RefundArchiver extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from(REFUND_SENDING)
                .routeDescription("where to send your refund piece")
                .routeId("refund-piece-route")
                .to( REFUND_PIECE_OUTPUT_DIR + "?fileName=${exchangeProperty[name]}-${exchangeProp}.txt")
                .end();


    }
}
