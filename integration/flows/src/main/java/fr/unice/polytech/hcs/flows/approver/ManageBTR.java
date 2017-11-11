package fr.unice.polytech.hcs.flows.approver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ManageBTR extends RouteBuilder{

    private final static String routeId = "approver-api";
    private int btrId;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
        ;

        rest("/btr")
                //.get("/{id}").to(APPROVER_EP_GET)
                .post().to(APPROVER_EP_POST).type(BusinessTravelRequest.class)
                //.put("/{id}").to(APPROVER_EP_PUT)
        ;

        // Get a BTR giving his id
        /*from(APPROVER_EP_GET)
                .routeId(routeId + "-get")
                .doTry()
                    .removeHeaders("CamelHttp*")
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .toD(APPROVER_REST_EP +  "${headers.id}")
                .doCatch(Exception.class)
                    .log(APPROVER_REST_EP + "${headers.id}")
                    .log("${exception.message}")
                    .log("${exception.stacktrace}")
                .end();
*/
        from(APPROVER_EP_POST)
                .log("poney")
                .routeId(routeId + "-post")
                    .removeHeaders("CamelHttp*")
                    .log("Petit poney")
                    .log("coucou")
                    .process(e -> {
                        System.out.println("poneyyyyyyy");
                        System.out.println(e.getIn().getBody().getClass());
                    })
                    .log("hello")
                    .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                    .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                    .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))
                    .to(APPROVER_REST_EP);
                    //.log("${body}")
                /*.doCatch(Exception.class)
                    //.log("${body}")
                    .log("${exception.message}")
                    .log("${exception.stacktrace}")
                    .log("something went wrong")
                *///.end();

        // Get a BTR giving his id
       /* from(APPROVER_EP_PUT)
                .routeId(routeId + "-put")
                .doTry()
                    .removeHeaders("CamelHttp*")
                    .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                    .toD(APPROVER_REST_EP +  "${headers.id}")
                .doCatch(Exception.class)
                    .log(APPROVER_REST_EP + "${headers.id}")
                    .log("${exception.message}")
                    .log("${exception.stacktrace}")
                    .log("Something bad happened")
                .end();*/
    }

    private void extractLastParameter(String uri){
           String[] params = uri.split("/");
           btrId = Integer.parseInt(params[params.length - 1]);
    }
}
