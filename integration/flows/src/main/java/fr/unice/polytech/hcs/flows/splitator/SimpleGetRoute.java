package fr.unice.polytech.hcs.flows.splitator;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DataFormatDefinition;

import java.io.Serializable;
import java.util.Map;


public abstract class SimpleGetRoute<In extends Serializable, Out extends Serializable> extends RouteBuilder {

    private final String routeUri;
    private final String endpoint;
    private final DataFormatDefinition dataFormatDef;
    private final Convertor<In, Object> genericReqConvertor;
    private final Convertor<Map, Out> specificResConvertor;
    private final String routeId;
    private final String routeDescription;

    public SimpleGetRoute(String routeUri,
                          String endpoint,
                          DataFormatDefinition dataFormatDef,
                          Convertor<In, Object> genericReqConvertor,
                          Convertor<Map, Out> specificResConvertor,
                          String routeId,
                          String routeDescription) {
        this.routeUri = routeUri;
        this.endpoint = endpoint;
        this.dataFormatDef = dataFormatDef;
        this.genericReqConvertor = genericReqConvertor;
        this.specificResConvertor = specificResConvertor;
        this.routeId = routeId;
        this.routeDescription = routeDescription;
    }

    @Override
    public void configure() throws Exception {
        from(routeUri)
                .routeId(routeId)
                .routeDescription(routeDescription)

                .log("Create specific request from generic request")
                .process(e -> e.getIn().setBody(genericReqConvertor.convert((In) e.getIn().getBody())))

                .log("Setting up request header")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))

                .log("Marshalling body into JSON") // TODO make dynamic url
                .marshal(dataFormatDef)

                .log("Sending to endpoint")
                .inOut(endpoint)
                .log("Received specific request result results")

                .log("Unmarshal JSON response to generic response")
                .unmarshal(dataFormatDef)
                .process(e -> e.getIn().setBody(specificResConvertor.convert((Map) e.getIn().getBody())))
        ;
    }
}
