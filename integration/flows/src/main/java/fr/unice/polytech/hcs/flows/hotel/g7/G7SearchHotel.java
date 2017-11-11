package fr.unice.polytech.hcs.flows.hotel.g7;

import fr.unice.polytech.hcs.flows.hotel.Hotel;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
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

//                .doTry()
                    .log("["+routeUri+"] Creating specific request from generic request")
                    .process(e -> e.getIn().setBody(genericReqConverter.convert((HotelSearchRequest) e.getIn().getBody())))

                    .log("["+routeUri+"] Converting specific request to XML request")
                    .bean(G7SearchHotel.class, "buildXMLRequest(${body})")

                    .log("["+routeUri+"] Setting up request header")
                    .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
                    .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/xml"))

                    .log("["+routeUri+"] Sending to endpoint")
                    .inOut(endpoint)
                    .log("["+routeUri+"] Received specific request result")

                    .log("["+routeUri+"] Unmarshalling response")
                    .unmarshal().jacksonxml()

                    .log("["+routeUri+"] Converting to generic response")
                    .process(e -> e.getIn().setBody(specificResConverter.convert((Map<String, Map<String, Map>>) e.getIn().getBody())))
//                .doCatch(Exception.class)
//                    .log("[" + routeUri + "] Something went wrong, setting response to null (${exception.message})")
//                    .process(e -> e.getIn().setBody(null))
//                .end()
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


    public static HotelSearchResponse mapToHsRes(Map<String, Map<String, Map>> xml) {
        if(xml == null) {
            return null;
        }

        Map<String, Object> hotelMap = (Map<String, Object>) xml.get("Body").get("searchHotelResponse").get("hotel_list");

        HotelSearchResponse hsr = new HotelSearchResponse();
        hsr.result = new ArrayList<>();
        Hotel hotel = new Hotel();
        hotel.name = (String) hotelMap.get("name");
        hotel.nightPrice = Double.parseDouble((String) hotelMap.get("price"));
        hotel.city = (String) hotelMap.get("city");
        hotel.address = (String) hotelMap.get("address");
        hotel.zipCode = ((String) hotelMap.get("address")).substring(0, 5);
        hsr.result.add(hotel);

        return hsr;
    }
}
