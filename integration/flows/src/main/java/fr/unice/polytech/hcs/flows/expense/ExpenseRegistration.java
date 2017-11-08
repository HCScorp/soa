package fr.unice.polytech.hcs.flows.expense;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.EXPENSE_EMAIL;

public class ExpenseRegistration extends RouteBuilder {

    private final static String routeId = "expense-api";

    @Override
    public void configure() throws Exception {
        String enclosure = "[ " + routeId + " ]";
        from(EXPENSE_EMAIL)
                .routeId(routeId)
                .log(enclosure + " unmarshaling email")
                .log("${body}")
                .unmarshal().json(JsonLibrary.Jackson, Expense.class)
                .log("${body}")
                .end();
    }
}
