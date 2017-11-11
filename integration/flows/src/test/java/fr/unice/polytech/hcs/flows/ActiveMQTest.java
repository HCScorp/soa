package fr.unice.polytech.hcs.flows;


import fr.unice.polytech.hcs.flows.car.SearchCar;
import fr.unice.polytech.hcs.flows.car.g2.G2SearchCar;
import fr.unice.polytech.hcs.flows.car.hcs.HCSSearchCar;
import fr.unice.polytech.hcs.flows.explanation.ExplanationProvider;
import fr.unice.polytech.hcs.flows.flight.SearchFlight;
import fr.unice.polytech.hcs.flows.flight.g1.G1SearchFlight;
import fr.unice.polytech.hcs.flows.flight.hcs.HCSSearchFlight;
import fr.unice.polytech.hcs.flows.hotel.SearchHotel;
import fr.unice.polytech.hcs.flows.hotel.g7.G7SearchHotel;
import fr.unice.polytech.hcs.flows.hotel.hcs.HCSSearchHotel;
import fr.unice.polytech.hcs.flows.utils.Endpoints;
import org.apache.activemq.broker.BrokerService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class ActiveMQTest extends CamelTestSupport {

    /**
     * Handling ActiveMQ
     */

    private static BrokerService brokerSvc;

    @BeforeClass
    public static void setUpActiveMQ() throws Exception {
        brokerSvc = new BrokerService();
        brokerSvc.setBrokerName("TestBroker");
        brokerSvc.addConnector("tcp://localhost:61616");
        brokerSvc.start();
    }

    @AfterClass
    public static void tearDownActiveMQ() throws Exception {
        brokerSvc.stop();
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
//        DeathPool deathPool = new DeathPool();
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
//                this.includeRoutes(deathPool);
//                this.setErrorHandlerBuilder(deathPool.getErrorHandlerBuilder());
                this.includeRoutes(new HCSSearchFlight());
                this.includeRoutes(new G1SearchFlight());
                this.includeRoutes(new SearchFlight());
                this.includeRoutes(new HCSSearchHotel());
                this.includeRoutes(new G7SearchHotel());
                this.includeRoutes(new SearchHotel());
                this.includeRoutes(new HCSSearchCar());
                this.includeRoutes(new G2SearchCar());
                this.includeRoutes(new SearchCar());
                this.includeRoutes(new ExplanationProvider());
                this.includeRoutes(new SearchFlight());
                // TODO
            }
        };
    }


    /**
     * Handling Mocks endpoints automatically
     */
    private static Map<String, String> mocks = new HashMap<>();

    @BeforeClass
    public static void loadEndpointsAsMocks() throws Exception {
        // Automatically loads all the constants defined in the Endpoints class
        Field[] fields = Endpoints.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) && f.getType().equals(String.class)) {
                mocks.put("" + f.get(""), "mock://" + f.get(""));
            }
        }
    }

    protected void isAvailableAndMocked(String name) {
        assertNotNull(context.hasEndpoint(name));
        assertNotNull(context.hasEndpoint(mocks.get(name)));
    }

    protected String mockStr(String name) {
        return mocks.get(name);
    }

    protected MockEndpoint mock(String name) {
        return getMockEndpoint(mocks.get(name));
    }

}