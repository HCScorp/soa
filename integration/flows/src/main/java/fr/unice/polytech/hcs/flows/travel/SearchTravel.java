package fr.unice.polytech.hcs.flows.travel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.SEARCH_TRAVEL;

public class SearchTravel extends RouteBuilder {

    private static final String host = "myDb";
    private static final String database = "hotel";
    private static final String collection = "hotels";

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
        ;

        rest("/travel")
                .post("/search")
                .type(TravelRequest.class)
                .outType(TravelResponse.class)
                .to(SEARCH_TRAVEL)
        ;

        from(SEARCH_TRAVEL)
                .routeId("search-travel")
                .routeDescription("Retrieve information for a specific travel")

                .log("[" + SEARCH_TRAVEL + "] Remove shitty headers (thx camel)")
                .removeHeaders("CamelHttp*")
                //.process(retrieveTravel)
                .to("mongodb:" + host + "?database=" + database + "&collection=" + collection + "&operation=findAll")
        //.to(Endpoints.SEARCH_TRAVEL_RESPONSE)
        ;

    }

    private static Processor retrieveTravel = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            TravelRequest request = (TravelRequest) exchange.getIn().getBody();
            TravelResponse response = retrieve(request);
            exchange.getIn().setBody(response);
        }

        private TravelResponse retrieve(TravelRequest request) {
            TravelResponse response = new TravelResponse();

            return response;
        }
    };
}
