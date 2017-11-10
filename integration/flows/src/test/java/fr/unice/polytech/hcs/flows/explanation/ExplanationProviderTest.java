package fr.unice.polytech.hcs.flows.explanation;

import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Travel;
import fr.unice.polytech.hcs.flows.refund.RefundArchiver;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;
import static org.apache.camel.builder.Builder.constant;

public class ExplanationProviderTest extends ActiveMQTest {

    Explanation explanation;

    @Override
    public String isMockEndpointsAndSkip() {
        return EXPLANATION_CHECKER + "|" + EXPLANATION_REFUSED  + "|" + EXPLANATION_REFUSED_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new ExplanationProvider();
    }

    @Before
    public void initExplanation() throws Exception {
        explanation = new Explanation();
        explanation.id = 10;
        explanation.explanation = "j'ai peur du noir. du coup je dors pas.";
    }

    @Test
    public void TestExplanationProviderChecker() throws InterruptedException {
//        // isAvailableAndMocked(EXPLANATION_CHECKER);
//        // assertNotNull(context.hasEndpoint(EXPLANATION_PROVIDER));
//        mock(EXPLANATION_CHECKER).expectedMessageCount(1);
//        mock(EXPLANATION_CHECKER).expectedHeaderReceived(Exchange.HTTP_METHOD, constant("POST"));
//        template.sendBody(EXPLANATION_PROVIDER, explanation);
//        // assertMockEndpointsSatisfied();
    }

}