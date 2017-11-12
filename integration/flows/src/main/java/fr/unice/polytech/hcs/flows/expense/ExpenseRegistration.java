package fr.unice.polytech.hcs.flows.expense;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ExpenseRegistration extends RouteBuilder {

    private final static String routeId = "expense-api";

    @Override
    public void configure() throws Exception {
        String enclosure = "[ " + routeId + " ]";
        from(EXPENSE_EMAIL)
                .routeId(routeId)
                .doTry()
                    .inOut(OCR_IN)
                .doCatch(Exception.class)
                    .log(enclosure + " something went wrong")
                    .log("${exception.stacktrace}");

        from(OCR_OUT)
                .doTry()
                    .process(e -> e.getIn().setBody(new ExpenseReportDB((ExpenseReport)e.getIn().getBody())))
                    .inOut(SAVE_TRAVEL_DATABASE_EP)
                .doCatch(Exception.class)
                    .log(enclosure + " something went wrong")
                    .log("${exception.message}")
                    .log("${exception.stacktrace}");


    }
}