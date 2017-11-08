package fr.unice.polytech.hcs.flows.hotel.g7;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.unice.polytech.hcs.flows.SpecificSearchTest;
import fr.unice.polytech.hcs.flows.hotel.Hotel;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchResponse;

import java.util.ArrayList;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.G7_SEARCH_HOTEL_EP;
import static fr.unice.polytech.hcs.flows.utils.Endpoints.G7_SEARCH_HOTEL_MQ;

public class G7SearchHotelTest extends SpecificSearchTest {

    public G7SearchHotelTest() {
        super(G7_SEARCH_HOTEL_EP, G7_SEARCH_HOTEL_MQ, new G7SearchHotel());
    }

    private final String hsResXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <soap:Body>\n" +
            "        <ns2:searchHotelResponse xmlns:ns2=\"http://gr7.polytech.unice.fr/soa/\">\n" +
            "            <hotel_list>\n" +
            "                <address>65573 Bowman Center</address>\n" +
            "                <availableRooms>60</availableRooms>\n" +
            "                <city>Berlin</city>\n" +
            "                <country>Germany</country>\n" +
            "                <name>Cephalotaxus Siebold Zucc. ex Endl.</name>\n" +
            "                <partner>false</partner>\n" +
            "                <price>91.71</price>\n" +
            "            </hotel_list>\n" +
            "        </ns2:searchHotelResponse>\n" +
            "    </soap:Body>\n" +
            "</soap:Envelope>";

    @Override
    public void initVariables() throws Exception {
        HotelSearchRequest hsr = new HotelSearchRequest();
        hsr.city = "Paris";
        hsr.dateFrom = "2017-10-21";
        hsr.dateTo = "2017-10-26";
        hsr.order = "ASCENDING";

        this.genericRequest = hsr;
        HotelSearchResponse resp = new HotelSearchResponse();
        resp.result = new ArrayList<>();

        Hotel h1 = new Hotel();
        h1.name = "Cephalotaxus Siebold Zucc. ex Endl.";
        h1.address = "65573 Bowman Center";
        h1.zipCode = "65573";
        h1.city = "Berlin";
        h1.nightPrice = 91.71;

        resp.result.add(h1);

        this.genericResponse = resp;
        this.specificResultJson = hsResXml;
    }
}