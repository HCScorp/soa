package fr.unice.polytech.hcs.flows.hotel.g7;

import fr.unice.polytech.hcs.flows.hotel.Hotel;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchResponse;
import fr.unice.polytech.hcs.flows.splitator.SimplePostRoute;
import fr.unice.polytech.hcs.flows.utils.Cast;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import java.util.Collection;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G7_SEARCH_HOTEL_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G7_SEARCH_HOTEL_MQ;

public class G7SearchHotel extends SimplePostRoute<HotelSearchRequest, HotelSearchResponse> {

    public G7SearchHotel() {
        super(G7_SEARCH_HOTEL_MQ,
                G7_SEARCH_HOTEL_EP,
                new JacksonXMLDataFormat(),
                G7HotelSearchRequest::new,
                G7SearchHotel::mapToHsRes,
                "g7-search-hotel-ws",
                "Send the hotel search request to the G7 hotel WS");
    }

    public static HotelSearchResponse mapToHsRes(Map<String, Object> map) {
        Collection<Map<String, Object>> hotels = (Collection<Map<String, Object>>) map.get("hotels"); // TODO

        HotelSearchResponse hsr = new HotelSearchResponse();
        hsr.result = new Hotel[hotels.size()];
        int i = 0;
        for (Map<String, Object> m : hotels) {
            Hotel h = new Hotel();
            h.name = (String) m.get("name");
            h.address = (String) m.get("address");
            h.city = (String) m.get("city");
            h.nightPrice = Cast.toDouble(m.get("price"));
            hsr.result[i++] = h;
        }

        return hsr;
    }
}
