package fr.unice.polytech.hcs.flows.splitator;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.io.Serializable;
import java.util.Map;


public class SimplePostJsonRoute<In extends Serializable, Out extends Serializable> extends RouteBuilder {

    private final String routeUri;
    private final String endpoint;
    private final Convertor<In, Object> genericReqConvertor;
    private final Convertor<Map, Out> specificResConvertor;
    private final String routeId;
    private final String routeDescription;

    public SimplePostJsonRoute(String routeUri, String endpoint,
                               Convertor<In, Object> genericReqConvertor,
                               Convertor<Map, Out> specificResConvertor,
                               String routeId, String routeDescription) {
        this.routeUri = routeUri;
        this.endpoint = endpoint;
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
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))

                .log("Marshalling body into JSON")
                .marshal().json(JsonLibrary.Jackson)

                .log("Sending to endpoint")
                .inOut(endpoint)
                .log("Received specific request result results")

                .log("Unmarshal JSON response to generic response")
                .unmarshal().json(JsonLibrary.Jackson)
                .process(e -> e.getIn().setBody(specificResConvertor.convert((Map) e.getIn().getBody())))
        ;
    }
}
