package fr.unice.polytech.hcs.flows.expense;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.OCR_IN;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.OCR_OUT;

public class OCR extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(OCR_IN)
                .log("OCR-izitation")
                .to(OCR_OUT);
    }
}
