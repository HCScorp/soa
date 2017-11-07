package fr.unice.polytech.hcs.flows.splitator;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.io.Serializable;
import java.util.Map;


public abstract class SimpleJsonGetRoute<In extends Serializable, Out extends Serializable> extends RouteBuilder {

    private final String routeUri;
    private final String endpoint;
    private final Converter<In, Object> genericRecConverter;
    private final Converter<Map, Out> specificResConverter;
    private final String routeId;
    private final String routeDescription;

    public SimpleJsonGetRoute(String routeUri,
                              String endpoint,
                              Converter<In, Object> genericRecConverter,
                              Converter<Map, Out> specificResConverter,
                              String routeId,
                              String routeDescription) {
        this.routeUri = routeUri;
        this.endpoint = endpoint;
        this.genericRecConverter = genericRecConverter;
        this.specificResConverter = specificResConverter;
        this.routeId = routeId;
        this.routeDescription = routeDescription;
    }

    @Override
    public void configure() throws Exception {
        from(routeUri)
                .routeId(routeId)
                .routeDescription(routeDescription)

                .log("["+routeUri+"] Creating specific request from generic request")
                .process(e -> e.getIn().setBody(genericRecConverter.convert((In) e.getIn().getBody())))

                .log("["+routeUri+"] Setting up request header")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))

                .log("["+routeUri+"] Preparing url to request")
                .marshal().json(JsonLibrary.Jackson)

                .log("["+routeUri+"] Sending to endpoint")
                .inOut(endpoint)
                .log("["+routeUri+"] Received specific request result")

                .log("["+routeUri+"] Unmarshalling response")
                .unmarshal().json(JsonLibrary.Jackson)

                .log("["+routeUri+"] Converting to generic response")
                .process(e -> e.getIn().setBody(specificResConverter.convert((Map) e.getIn().getBody())))
//                .log("["+routeUri+"] OUT: ${body}")
//        from(routeUri)
//                .routeId(routeId)
//                .routeDescription(routeDescription)
//
//                .log("Create specific request from generic request")
//                .process(e -> e.getIn().setBody(genericReqConverter.convert((In) e.getIn().getBody())))
//
//                .log("Setting up request header")
//                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
//
//                .log("Marshalling body ") // TODO make dynamic url
//                .marshal(dataFormatDef)
//
//                .log("Sending to endpoint")
//                //.toD(simple("freemarker://templateHome/${body.templateName}.ftl"))
//                .inOut(endpoint)
//                .log("Received specific request result")
//
//                .log("Unmarshalling response")
//                .unmarshal(dataFormatDef)
//
//                .log("Converting to generic response")
//                .process(e -> e.getIn().setBody(specificResConverter.convert((Map) e.getIn().getBody())))
        ;
    }

}
