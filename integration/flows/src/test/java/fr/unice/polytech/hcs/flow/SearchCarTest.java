package fr.unice.polytech.hcs.flow;

import fr.unice.polytech.hcs.flows.hcs.car.Car;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import fr.unice.polytech.hcs.flows.hcs.car.SearchCar;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchCarTest extends ActiveMQTest {

    @Override public String isMockEndpointsAndSkip() { return "http:car:8080/flight-service-document/car"; }
    @Override protected RouteBuilder createRouteBuilder() throws Exception { return new SearchCar(); }

    private Car tesla;

    @Before
    public void init(){
        tesla = new Car();
        List<LocalDate> booked = new ArrayList<>();
        booked.add(LocalDate.of(2017, 12, 1));
        tesla.setBookedDay(booked);
        tesla.setCity("toulon");
        tesla.setCompany("tesla");
        tesla.setModel("S");
        tesla.setNumberPlate("9867-67865");
    }

    @Test
    public void TestSearchFlight(){
//        // Asserting endpoints existence
//        assertNotNull(context.hasEndpoint(Endpoints.REGISTER_A_CITIZEN));
//        assertNotNull(context.hasEndpoint(Endpoints.REGISTRATION_ENDPOINT));
//
//        // Configuring expectations on the mocked endpoint
//        String mock = "mock://"+Endpoints.REGISTRATION_ENDPOINT;
//        assertNotNull(context.hasEndpoint(mock));
//        getMockEndpoint(mock).expectedMessageCount(1);
//        getMockEndpoint(mock).expectedHeaderReceived("Content-Type", "application/json");
//        getMockEndpoint(mock).expectedHeaderReceived("Accept", "application/json");
//        getMockEndpoint(mock).expectedHeaderReceived("CamelHttpMethod", "POST");
//
//        // Sending Johm for registration
//        template.sendBody(Endpoints.REGISTER_A_CITIZEN, john);
//
//        getMockEndpoint(mock).assertIsSatisfied();
//
//        // As the assertions are now satisfied, one can access to the contents of the exchanges
//        String request = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);
//
//        String expected = "{\n" +
//                "  \"event\": \"REGISTER\",\n" +
//                "  \"citizen\": {\n" +
//                "    \"last_name\": \"Doe\",\n" +
//                "    \"first_name\": \"John\",\n" +
//                "    \"ssn\": \"1234567890\",\n" +
//                "    \"zip_code\": \"06543\",\n" +
//                "    \"address\": \"nowhere, middle of\",\n" +
//                "    \"birth_year\": \"1970\"\n" +
//                "  }\n" +
//                "}";
//        JSONAssert.assertEquals(expected, request, false);
//
//        assertNotNull("ActiveMq is Null ! ", context.hasEndpoint("activemq:car"));
//        assertNotNull("FlightWs is Null !",context.hasEndpoint( "http:car:8080/car-service-document/car"));
//        // Configuring expectations on the mocked endpoint
//        String mock = "mock://"+"http:car:8080/car-service-document/car";
//        assertNotNull(context.hasEndpoint(mock));
//        getMockEndpoint(mock).expectedHeaderReceived("Content-Type", "application/json");
//        getMockEndpoint(mock).expectedHeaderReceived("Accept", "application/json");
//        getMockEndpoint(mock).expectedHeaderReceived("CamelHttpMethod", "POST");
    }


}
