package fr.unice.polytech.hcs.flows.splitator;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;


public abstract class SimpleJsonGetRoute<In extends Serializable, Out extends Serializable> extends RouteBuilder {

    private final String routeUri;
    private final String endpoint;
    private final Function<In, Object> genericRecConverter;
    private final Function<InputStream, Out> specificResConverter;
    private final Function<Object, String> urlGenerator;
    private final String routeId;
    private final String routeDescription;

    public SimpleJsonGetRoute(String routeUri,
                              String endpoint,
                              Function<In, Object> genericRecConverter,
                              Function<InputStream, Out> specificResConverter,
                              Function<Object, String> urlGenerator,
                              String routeId,
                              String routeDescription) {
        this.routeUri = routeUri;
        this.endpoint = endpoint;
        this.genericRecConverter = genericRecConverter;
        this.specificResConverter = specificResConverter;
        this.urlGenerator = urlGenerator;
        this.routeId = routeId;
        this.routeDescription = routeDescription;
    }

    @Override
    public void configure() throws Exception {
        from(routeUri)
                .routeId(routeId)
                .routeDescription(routeDescription)

                .log("[" + routeUri + "] Creating specific request from generic request")
                .log("["+routeUri+"] IN: ${body}")
                .process(e -> e.getIn().setBody(genericRecConverter.apply((In) e.getIn().getBody())))
                .process(e -> e.getIn().setHeader("dynamicUrl", urlGenerator.apply(e.getIn().getBody())))
                .log("["+routeUri+"] OUT: ${body}")

                .log("[" + routeUri + "] Setting up request header")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))

                .log("[" + routeUri + "] Preparing url to request")
                .marshal().json(JsonLibrary.Jackson)
                .log("["+routeUri+"] OUT: ${body}")

                .log("[" + routeUri + "] Sending to endpoint")
                .toD(endpoint + "${header.dynamicUrl}")
                .log("[" + routeUri + "] Received specific request result")

                .log("[" + routeUri + "] Unmarshalling response && converting to generic response")
                .process(e -> e.getIn().setBody(specificResConverter.apply((InputStream) e.getIn().getBody())))
                .log("["+routeUri+"] OUT: ${body}")
        ;
    }

}
