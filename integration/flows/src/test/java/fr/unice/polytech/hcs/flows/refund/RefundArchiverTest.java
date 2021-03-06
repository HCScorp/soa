
package fr.unice.polytech.hcs.flows.refund;

import fr.unice.polytech.hcs.flows.ActiveMQTest;
import fr.unice.polytech.hcs.flows.expense.Expense;
import fr.unice.polytech.hcs.flows.expense.Status;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

import java.util.ArrayList;

public class RefundArchiverTest extends ActiveMQTest {


    Travel borabora;

    @Override
    public String isMockEndpoints() {
        return "";
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RefundArchiver();
    }


    @Test
    public void TestRefundArchiver() throws InterruptedException {
        borabora = new Travel();
        Expense expense = new Expense();
        expense.category = "plage-vietnamien";
        expense.evidence = "LaRue/Tiger/Pina-Colada";
        expense.price = 120.2;
        Expense expense1 = new Expense();
        expense1.category = "playa";
        expense1.evidence = "pina-colada";
        expense1.price = 156.4;
        ArrayList<Expense> expenseArrayList = new ArrayList<>();
        expenseArrayList.add(expense);
        expenseArrayList.add(expense1);
        borabora.documents = expenseArrayList;
        borabora.status = Status.REFUND_ACCEPTED;
        borabora.travelId = "10";
    }

}