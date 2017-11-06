package fr.unice.polytech.hcs.flows.hotel.g7;

import fr.unice.polytech.hcs.flows.hotel.Hotel;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.Converter;
import fr.unice.polytech.hcs.flows.utils.Cast;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Collection;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G7_SEARCH_HOTEL_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G7_SEARCH_HOTEL_MQ;

public class G7SearchHotel extends RouteBuilder {

    private final String routeUri = G7_SEARCH_HOTEL_MQ;
    private final String endpoint = G7_SEARCH_HOTEL_EP;
    private final Converter<HotelSearchRequest, Object> genericReqConverter = G7HotelSearchRequest::new;
    private final Converter<Map, HotelSearchResponse> specificResConverter = G7SearchHotel::mapToHsRes;
    private final String routeId = "g7-search-hotel-ws";
    private final String routeDescription = "Send the hotel search request to the G7 hotel WS";

    @Override
    public void configure() throws Exception {
        from(routeUri)
                .routeId(routeId)
                .routeDescription(routeDescription)

                .log("Creating specific request from generic request")
                .process(e -> e.getIn().setBody(genericReqConverter.convert((HotelSearchRequest) e.getIn().getBody())))

                .log("Converting specific request to XML request")
                .bean(G7SearchHotel.class, "buildXMLRequest(${body})")

                .log("["+routeUri+"] Setting up request header")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/xml"))

                .log("Sending to endpoint")
                .inOut(endpoint)
                .log("Receiving specific request result")

                .log("Unmarshalling JSON response")
                .unmarshal().jacksonxml()

                .log("Converting specific response to generic response")
                .process(e -> e.getIn().setBody(specificResConverter.convert((Map) e.getIn().getBody())))
        ;
    }

    public static String buildXMLRequest(G7HotelSearchRequest g7Hsr) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soa=\"http://gr7.polytech.unice.fr/soa/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soa:searchHotel>\n" +
                "         <searchParams>\n" +
                "            <address>" + g7Hsr.address + "</address>\n" +
                "            <checkin>" + g7Hsr.checkin + "</checkin>\n" +
                "            <checkout>" + g7Hsr.checkout + "</checkout>\n" +
                "            <city>" + g7Hsr.city + "</city>\n" +
                "            <resultNumber>" + g7Hsr.resultNumber + "</resultNumber>\n" +
                "            <sortOrder>" + g7Hsr.sortOrder + "</sortOrder>\n" +
                "         </searchParams>\n" +
                "      </soa:searchHotel>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }


    public static HotelSearchResponse mapToHsRes(Object o) {
        if(o == null) {
            return null;
        }

        System.out.println("lalalalalala: " + o);
//        Collection<Map<String, Object>> hotels = (Collection<Map<String, Object>>) o.get("hotel_list"); // TODO
//        System.out.println("lololo: " + hotels);
        // TODO
        // TODO
        // TODO
        // TODO

        HotelSearchResponse hsr = new HotelSearchResponse();
//        hsr.result = new Hotel[hotels.size()];
//        int i = 0;
//        for (Map<String, Object> m : hotels) {
//            Hotel h = new Hotel();
//            h.name = (String) m.get("name");
//            h.address = (String) m.get("address");
//            h.city = (String) m.get("city");
//            h.nightPrice = Cast.toDouble(m.get("price"));
//            hsr.result[i++] = h;
//        }

        return hsr;
    }
}
