package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;

import java.io.InputStream;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ExpenseRegistration extends RouteBuilder {

    private final static String routeId = "expense-api";

    @Override
    public void configure() throws Exception {
        String enclosure = "[ " + routeId + " ]";
        from(EXPENSE_EMAIL)
                .routeId(routeId)
                .doTry()
                    .process(exchange -> {
                        InputStream is = exchange.getIn().getBody(InputStream.class);
                        exchange.getIn().setBody(new ObjectMapper().readValue(is , ExpenseReport.class));
                    })
                    .inOut(OCR_IN)
                .doCatch(Exception.class)
                    .log(enclosure + " something went wrong")
                    .log("${exception.stacktrace}");

        from(OCR_OUT)
                .doTry()
                    .inOut(EXPENSE_DATABASE)
                .doCatch(Exception.class)
                    .log(enclosure + " something went wrong")
                    .log("${exception.stacktrace}");


    }
}