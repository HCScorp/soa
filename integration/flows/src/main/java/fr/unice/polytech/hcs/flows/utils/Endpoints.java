package fr.unice.polytech.hcs.flows.utils;

public class Endpoints {

    // File inputs
    public static final String CSV_INPUT_FILE_FLIGHTS = "file:/servicemix/camel/input?fileName=flights.csv";
    public static final String CSV_INPUT_FILE_HOTELS = "file:/servicemix/camel/input?fileName=hotels.csv";
    public static final String CSV_INPUT_FILE_CARS = "file:/servicemix/camel/input?fileName=cars.csv";

    public static final String SEARCH_FLIGHT_INPUT = "direct:search-flight";
    public static final String SEARCH_HOTEL_INPUT = "direct:search-hotel";
    // TODO HCS + others groups ?

    // File outputs
    // public static final String TODO = "file:/servicemix/camel/output";

    // Internal message queues (HCS)
    public static final String SEARCH_FLIGHT_MQ = "activemq:search-flight";
    public static final String HCS_SEARCH_FLIGHT_MQ = "activemq:hcs-flight";
    public static final String G1_SEARCH_FLIGHT_MQ = "activemq:g1-flight";

    public static final String SEARCH_HOTEL_MQ = "activemq:search-hotel";
    public static final String HCS_SEARCH_HOTEL_MQ = "activemq:hcs-hotel";
    public static final String G7_SEARCH_HOTEL_MQ = "activemq:g7-hotel";

    public static final String SEARCH_CAR_MQ = "activemq:search-car";
    public static final String HCS_SEARCH_CAR_MQ = "activemq:hcs-car";
    public static final String G2_SEARCH_CAR_MQ = "activemq:g2-car";


    // Direct endpoints (flow modularity without a message queue overhead)
    // public static final String TODO    = "direct:todo";
    public static final String COMPARATOR = "direct:comparator";

    // External partners (HCS)
    public static final String HCS_SEARCH_FLIGHT_EP = "http:hcs-flight:8080/flight-service-document/flight?bridgeEndpoint=true&throwExceptionOnFailure=false";
    public static final String HCS_SEARCH_HOTEL_EP = "http:hcs-hotel:8080/hotel-service-document/hotel?bridgeEndpoint=true&throwExceptionOnFailure=false";
    public static final String HCS_SEARCH_CAR_EP = "http:hcs-car:8080/car-service-document/car?bridgeEndpoint=true&throwExceptionOnFailure=false";

    public static final String G1_SEARCH_FLIGHT_EP = "http:g1-flight:8080/flight-service-document/flight?bridgeEndpoint=true&throwExceptionOnFailure=false";
    public static final String G7_SEARCH_HOTEL_EP = "http:g7-hotel:8080/hotel-service-rpc/hotel?bridgeEndpoint=true&throwExceptionOnFailure=false";
    public static final String G2_SEARCH_CAR_EP = "http:g2-car:8080/car-service-rest/car?bridgeEndpoint=true&throwExceptionOnFailure=false";


    // External partners (other groups)
    // TODO public static final String G1_SEARCH_FLIGHT_EP = "http:g1-flight:8080/flight-service-document/flight";
    // TODO public static final String G7_SEARCH_HOTEL_EP = "http:g7-hotel:8080/hotel-service-document/hotel";
    // TODO public static final String G2_SEARCH_CAR_EP = "http:g2-car:8080/flight-service-document/car";


    // MoneySaver Message Queue
    public static final String MONEY_ANALYSER_MQ = "activemq:money-savior";
    public static final String MONEY_ANALYSER_EP = "file:/servicemix/camel/input?fileName=out.csv";

    // Dead letters channel TODO
    // public static final String DEATH_POOL = "activemq:global:dead";

    // Archiver route
    public static final String REFUND_SENDING = "activemq:refund-piece";
    public static final String REFUND_PIECE_OUTPUT_DIR = "file:/servicemix/camel/output";

    public static final String REFUND_SENDING_EP = "";


}



