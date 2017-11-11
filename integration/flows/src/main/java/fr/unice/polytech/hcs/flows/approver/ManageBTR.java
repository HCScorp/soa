package fr.unice.polytech.hcs.flows.approver;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.*;

public class ManageBTR extends RouteBuilder{

    private final static String routeId = "approver-api";
    private int btrId;

    @Override
    public void configure() throws Exception {
        /*restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
        ;*/

        rest("/btr/")
                .get("/{id}").to(APPROVER_EP_GET)
                .post().to(APPROVER_EP_POST)
                .put("/{id}").to(APPROVER_EP_PUT)
        ;

        // Get a BTR giving his id
        from(APPROVER_EP_GET)
                .routeId(routeId + "-get")
                .doTry()
                    .removeHeaders("CamelHttp*")
                    .toD(APPROVER_REST_EP +  "${headers.id}")
                .doCatch(Exception.class)
                    .log(APPROVER_REST_EP + "${headers.id}")
                    .log("${exception.message}")
                    .log("${exception.stacktrace}")
                    .log("Something bad happened");

        from(APPROVER_EP_POST)
                .routeId(routeId + "-post")
                .doTry()
                    .removeHeader("CamelHttp*")
                    .toD(APPROVER_REST_EP)
                .doCatch(Exception.class)
                    .log("${exception.message}")
                    .log("${exception.stacktrace}");

        // Get a BTR giving his id
        from(APPROVER_EP_PUT)
                .routeId(routeId + "-put")
                .doTry()
                .removeHeaders("CamelHttp*")
                .toD(APPROVER_REST_EP +  "${headers.id}")
                .doCatch(Exception.class)
                .log(APPROVER_REST_EP + "${headers.id}")
                .log("${exception.message}")
                .log("${exception.stacktrace}")
                .log("Something bad happened");
    }

    private void extractLastParameter(String uri){
           String[] params = uri.split("/");
           btrId = Integer.parseInt(params[params.length - 1]);
    }
}
