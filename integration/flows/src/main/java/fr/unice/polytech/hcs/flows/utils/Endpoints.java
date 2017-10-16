package fr.unice.polytech.hcs.flows.utils;

public class Endpoints {

    // File inputs
    public static final String CSV_INPUT_FILE_FLIGHTS    = "file:/servicemix/camel/input?fileName=flights.csv";
    public static final String CSV_INPUT_FILE_HOTELS = "file:/servicemix/camel/input?fileName=hotels.csv";
    public static final String CSV_INPUT_FILE_CARS = "file:/servicemix/camel/input?fileName=cars.csv";
    // TODO HCS + others groups ?

    // File outputs
    // public static final String TODO = "file:/servicemix/camel/output";

    // Internal message queues (HCS)
    public static final String HCS_SEARCH_FLIGHT_MQ = "activemq:hcs-flight";
    public static final String HCS_SEARCH_HOTEL_MQ = "activemq:hcs-hotel";
    public static final String HCS_SEARCH_CAR_MQ = "activemq:hcs-car";

    // TODO public static final String G1_SEARCH_FLIGHT_MQ = "activemq:g1-flight";
    // TODO public static final String G7_SEARCH_HOTEL_MQ = "activemq:g7-hotel";
    // TODO public static final String G2_SEARCH_CAR_MQ = "activemq:g2-car";

    // Direct endpoints (flow modularity without a message queue overhead)
    // public static final String TODO    = "direct:todo";

    // External partners (HCS)
    public static final String HCS_SEARCH_FLIGHT_EP = "http:hcs-flight:8080/flight-service-document/flight";
    public static final String HCS_SEARCH_HOTEL_EP = "http:hcs-hotel:8080/hotel-service-document/hotel";
    public static final String HCS_SEARCH_CAR_EP = "http:hcs-car:8080/car-service-document/car";

    // External partners (other groups)
    // TODO public static final String G1_SEARCH_FLIGHT_EP = "http:g1-flight:8080/flight-service-document/flight";
    // TODO public static final String G7_SEARCH_HOTEL_EP = "http:g7-hotel:8080/hotel-service-document/hotel";
    // TODO public static final String G2_SEARCH_CAR_EP = "http:g2-car:8080/flight-service-document/car";

    // Dead letters channel TODO
    // public static final String DEATH_POOL = "activemq:global:dead";
}
