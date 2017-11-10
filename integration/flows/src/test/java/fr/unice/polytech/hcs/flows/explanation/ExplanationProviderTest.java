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
        return EXPLANATION_CHECKER + "|" + EXPLANATION_REFUSED + "|" + EXPENSE_DATABASE + "|" + EXPLANATION_REFUSED_EP;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new ExplanationProvider();
    }

    @Before
    public void initExplanation() throws Exception {
        Travel borabora = new Travel();
        Expense expense = new Expense();
        expense.category = "plage-vietnamien";
        expense.evidence = "LaRue/Tiger/Pina-Colada";
        expense.price = 120;
        Expense expense1 = new Expense();
        expense1.category = "playa";
        expense1.evidence = "pina-colada";
        expense1.price = 156;
        ArrayList<Expense> expenseArrayList = new ArrayList<>();
        expenseArrayList.add(expense);
        expenseArrayList.add(expense1);
        borabora.documents = expenseArrayList;
        borabora.status = "cocotier-vietnam";
        borabora.travelId = 10;

        explanation = new Explanation();
        explanation.travel = borabora;
        explanation.explanation = "j'ai peur du noir. du coup je dors pas.";
    }

    @Test
    public void TestExplanationProviderChecker() throws InterruptedException {
        isAvailableAndMocked(EXPLANATION_CHECKER);

        mock(EXPLANATION_CHECKER).expectedMessageCount(1);
        mock(EXPLANATION_CHECKER).expectedHeaderReceived(Exchange.HTTP_METHOD, constant("POST"));
        template.sendBody(EXPLANATION_PROVIDER, explanation);
        assertMockEndpointsSatisfied();
    }

}