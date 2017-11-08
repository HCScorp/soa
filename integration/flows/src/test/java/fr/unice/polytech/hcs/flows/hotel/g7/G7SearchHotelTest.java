package fr.unice.polytech.hcs.flows.hotel.g7;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.unice.polytech.hcs.flows.SpecificSearchTest;
import fr.unice.polytech.hcs.flows.hotel.HotelSearchRequest;

import java.util.Map;

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
            "                <name>Cephalotaxus Siebold &amp; Zucc. ex Endl.</name>\n" +
            "                <partner>false</partner>\n" +
            "                <price>91.71</price>\n" +
            "            </hotel_list>\n" +
            "            <hotel_list>\n" +
            "                <address>552 Grayhawk Lane</address>\n" +
            "                <availableRooms>4</availableRooms>\n" +
            "                <city>Berlin</city>\n" +
            "                <country>Germany</country>\n" +
            "                <name>Viguiera reticulata S. Watson</name>\n" +
            "                <partner>false</partner>\n" +
            "                <price>93.84</price>\n" +
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
        this.genericResponse = G7SearchHotel.mapToHsRes(new XmlMapper().readValue(hsResXml, Map.class));
        this.specificResultJson = hsResXml;
    }
}