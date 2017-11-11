package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;

import java.io.InputStream;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.OCR_IN;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.OCR_OUT;

public class OCR extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(OCR_IN)
                .doTry()
                    .log("OCR-izitation")
                    .process(exchange -> {
                        InputStream is = exchange.getIn().getBody(InputStream.class);
                        exchange.getIn().setBody(new ObjectMapper().readValue(is , ExpenseReport.class));
                    })
                    .to(OCR_OUT)
                .doCatch(Exception.class)
                    .log("Something went wrong")
                    .log("${exception.message}")
                    .log("${exception.stacktrace}");

    }
}
