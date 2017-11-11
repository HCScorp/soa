package fr.unice.polytech.hcs.flows.splitator;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.io.Serializable;
import java.util.Map;


public abstract class SimpleJsonPostRoute<In extends Serializable, Out extends Serializable> extends RouteBuilder {

    private final String routeUri;
    private final String endpoint;
    private final Converter<In, Object> genericRecConverter;
    private final Converter<Map, Out> specificResConverter;
    private final String routeId;
    private final String routeDescription;

    private final ObjectMapper mapper;
    private final String prefix;

    public SimpleJsonPostRoute(String routeUri,
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

        this.mapper = new ObjectMapper();
        this.mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        this.prefix = "[" + routeUri + "] ";
    }

    @Override
    public void configure() throws Exception {

        from(routeUri)
                .routeId(routeId)
                .routeDescription(routeDescription)

//                .doTry()
                .log(l("Creating specific request from generic request"))
                .log(l("IN: ${body}"))
                .process(e -> e.getIn().setBody(genericRecConverter.convert((In) e.getIn().getBody())))
                .log(l("OUT: ${body}"))

                .log(l("Setting up request header"))
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))

                .log(l("Marshalling body"))
                .marshal().json(JsonLibrary.Jackson)
                .log(l("OUT: ${body}"))

                .log(l("Sending to endpoint"))
                .inOut(endpoint)
                .log(l("Received specific request result"))

                .log(l("Unmarshalling response"))
                .unmarshal().json(JsonLibrary.Jackson)
                .log(l("OUT: ${body}"))

                .log(l("Converting to generic response"))
                .process(e -> e.getIn().setBody(specificResConverter.convert((Map) e.getIn().getBody())))
                .log(l("OUT: ${body}"))
//                .doCatch(java.net.UnknownHostException.class)
//                    .log(l("Something went wrong, setting response to null (${exception.message})"))
//                    .process(e -> e.getIn().setBody(null))
//                .end()
        ;
    }

    private String l(String msg) {
        return prefix + msg;
    }
}
