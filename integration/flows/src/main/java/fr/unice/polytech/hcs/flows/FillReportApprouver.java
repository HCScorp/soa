package fr.unice.polytech.hcs.flows;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FillReportApprouver extends RouteBuilder {

    private CamelContext camelContext;


    public FillReportApprouver() {

        camelContext = new DefaultCamelContext();
    }

    public FillReportApprouver(CamelContext context) {
        super(context);
    }

    @Override
    public void configure() throws Exception {

    }
}
