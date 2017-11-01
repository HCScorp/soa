package fr.unice.polytech.hcs.flows.utils;

public class Endpoints {

    // File inputs
    public static final String CSV_INPUT_FILE_FLIGHTS = "file:/servicemix/camel/input?fileName=flights.csv";
    public static final String CSV_INPUT_FILE_HOTELS = "file:/servicemix/camel/input?fileName=hotels.csv";
    public static final String CSV_INPUT_FILE_CARS = "file:/servicemix/camel/input?fileName=cars.csv";

    // File outputs
    // public static final String TODO = "file:/servicemix/camel/output";

    // Internal message queues (flight)
    public static final String SEARCH_FLIGHT_MQ = "activemq:search-flight";
    public static final String HCS_SEARCH_FLIGHT_MQ = "activemq:hcs-flight";
    public static final String G1_SEARCH_FLIGHT_MQ = "activemq:g1-flight";

    // Internal message queues (hotel)
    public static final String SEARCH_HOTEL_MQ = "activemq:search-hotel";
    public static final String HCS_SEARCH_HOTEL_MQ = "activemq:hcs-hotel";
    public static final String G7_SEARCH_HOTEL_MQ = "activemq:g7-hotel";

    // Internal message queues (car)
    public static final String SEARCH_CAR_MQ = "activemq:search-car";
    public static final String HCS_SEARCH_CAR_MQ = "activemq:hcs-car";
    public static final String G2_SEARCH_CAR_MQ = "activemq:g2-car";


    // Direct endpoints (flow modularity without a message queue overhead)
    public static final String SEARCH_FLIGHT_INPUT = "direct:search-flight";
    public static final String SEARCH_HOTEL_INPUT = "direct:search-hotel";
    public static final String SEARCH_CAR_INPUT = "direct:search-car";

    // External partners (HCS)
    public static final String HCS_SEARCH_FLIGHT_EP = "http:hcs-flight:8080/flight-service-document/flight"; //?bridgeEndpoint=true"; // &throwExceptionOnFailure=false
    public static final String HCS_SEARCH_HOTEL_EP = "http:hcs-hotel:8080/hotel-service-document/hotel"; //?throwExceptionOnFailure=false"; // &throwExceptionOnFailure=false
    public static final String HCS_SEARCH_CAR_EP = "http:hcs-car:8080/car-service-document/car"; //?throwExceptionOnFailure=false"; // &throwExceptionOnFailure=false

    // External partners (other groups)
    public static final String G1_SEARCH_FLIGHT_EP = "http:g1-flight:8080/flight-service-document/flight"; //?throwExceptionOnFailure=false"; // &throwExceptionOnFailure=false
    public static final String G7_SEARCH_HOTEL_EP = "http:g7-hotel:8080/hotel-service-rpc/hotel"; //?throwExceptionOnFailure=false"; // &throwExceptionOnFailure=false
    public static final String G2_SEARCH_CAR_EP = "http:g2-car:8080/car-service-rest/car"; //?throwExceptionOnFailure=false"; // &throwExceptionOnFailure=false

    // MoneySaver Message Queue
    public static final String MONEY_ANALYSER_MQ = "activemq:money-savior";
    public static final String MONEY_ANALYSER_EP = "file:/servicemix/camel/input?fileName=out.csv";

    // Dead letters channel TODO
    // public static final String DEATH_POOL = "activemq:global:dead";

    // Archiver route
    public static final String REFUND_SENDING = "direct:refund-piece";
    public static final String REFUND_PIECE_OUTPUT_DIR = "file:/servicemix/camel/output";

    public static final String REFUND_SENDING_EP = "";


}



