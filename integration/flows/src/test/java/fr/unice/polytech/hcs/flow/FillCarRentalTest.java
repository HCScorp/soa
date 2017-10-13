package fr.unice.polytech.hcs.flow;

import fr.unice.polytech.hcs.flows.data.Car;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import fr.unice.polytech.hcs.flows.FillCarRental;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FillCarRentalTest extends ActiveMQTest {

    @Override public String isMockEndpointsAndSkip() { return "http:car:8080/flight-service-document/car"; }
    @Override protected RouteBuilder createRouteBuilder() throws Exception { return new FillCarRental(); }

    private Car Tesla;

// todo improve dat
    @Before
    public void init(){
        Tesla = new Car();
        List<LocalDate> booked = new ArrayList<>();
        booked.add(LocalDate.of(2017, 12, 1));
        Tesla.setBookedDay(booked);
        Tesla.setCity("toulon");
        Tesla.setCompagny("Tesla");
        Tesla.setModel("S");
        Tesla.setNumberPlate("9867-67865");
    }

    @Test
    public void TestFillCarRental(){
        assertNotNull(context.hasEndpoint("activemq:flight"));
        assertNotNull(context.hasEndpoint("http:car:8080/flight-service-document/car"));
        
        // Configuring expectations on the mocked endpoint
        String mock = "mock://"+"http:car:8080/flight-service-document/car";
        assertNotNull(context.hasEndpoint(mock));
        getMockEndpoint(mock).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("CamelHttpMethod", "POST");
    }


}
