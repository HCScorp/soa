package fr.unice.polytech.hcs.flows.expense;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.EXPENSE_EMAIL;

public class ExpenseRegistration extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(EXPENSE_EMAIL)
                .log("Poney")
                .to("log:mailnew");
    }
}
