package fr.unice.polytech.hcs.flows.approver;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ManageBTR extends RouteBuilder {

    private CamelContext camelContext;


    public ManageBTR() {
        camelContext = new DefaultCamelContext();
    }

    public ManageBTR(CamelContext context) {
        super(context);
    }

    @Override
    public void configure() throws Exception {

    }
}
