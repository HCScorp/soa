package fr.unice.polytech.hcs.flows.refund;

import fr.unice.polytech.hcs.flows.ActiveMQTest;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_PIECE_OUTPUT_DIR;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_SENDING;

public class RefundArchiverTest extends ActiveMQTest {


    RefundRequest borabora;

    @Override
    public String isMockEndpoints() {
        return REFUND_PIECE_OUTPUT_DIR + "?fileName=ici.txt" + "|" + REFUND_SENDING;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RefundArchiver();
    }

    @Before
    public void init() {
        borabora = new RefundRequest();
        borabora.setAmount(10000);
        borabora.setName("cesar");
        borabora.setCity("bora bora");
        borabora.setDate("2017-10-12");
        borabora.setReason("business");
    }

    @Test
    public void TestRefundArchiver() throws InterruptedException {
        assertNotNull(context.hasEndpoint(REFUND_SENDING));
//        assertNotNull(context.hasEndpoint(REFUND_PIECE_OUTPUT_DIR + "?fileName=ici.txt"));
//        template.requestBody(REFUND_SENDING, borabora, RefundRequest.class);

    }

}
