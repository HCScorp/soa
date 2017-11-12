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
    public static final String HCS_SEARCH_FLIGHT_EP = "http:hcs-flight:8080/flight-service-document/flight";
    public static final String HCS_SEARCH_HOTEL_EP = "http:hcs-hotel:8080/hotel-service-document/hotel";
    public static final String HCS_SEARCH_CAR_EP = "http:hcs-car:8080/car-service-document/car";

    // External partners (other groups)
    public static final String G1_SEARCH_FLIGHT_EP = "http:g1-flight:8080/tcs-service-flights/flights";
    public static final String G7_SEARCH_HOTEL_EP = "http:g7-hotel:8080/soa-service-hotels/HotelSearchImplService";
    public static final String G2_SEARCH_CAR_EP = "http:g2-car:8080/voiture";

    // MoneySaver Message Queue
    public static final String MONEY_ANALYSER_MQ = "activemq:money-savior";
    public static final String MONEY_ANALYSER_EP = "file:/servicemix/camel/input?fileName=out.csv";

    // Dead letters channel
     public static final String ERROR_MQ = "jms:queue:dead";

    // Archiver route
    public static final String REFUND_SENDING = "activemq:refund-piece";


    public static final String EXPENSE_EMAIL = "file:/servicemix/camel/input/email";
    public static final String EXPENSE_DATABASE = "mongodb:myDb?database=expense&collection=expenses&operation=insert";

    public static final String EXPLANATION_PROVIDER = "activemq:explanation-provider";
    public static final String EXPLANATION_CHECKER = "activemq:explanation-checker";

    public static final String EXPLANATION_ANSWER = "activemq:explanation-answer";

    public static final String EXPLANATION_REFUSED = "direct:explanation-refused";
    public static final String EXPLANATION_ACCEPTED = "direct:explanation-acceptRefund";


    // Travel
    public static final String SEARCH_TRAVEL = "activemq:search-travel";
    public static final String GET_TRAVEL = "direct:get-travel";
    public static final String SEARCH_TRAVEL_DATABASE_EP = "mongodb:myDb?database=expense&collection=expenses&operation=findOneByQuery";

    public static final String SAVE_TRAVEL_DATABASE_EP = "mongodb:myDb?database=expense&collection=expenses&operation=save";

    public static final String END_TRAVEL = "activemq:end-travel";
    public static final String ACCEPT_REFUND = "direct:automatic-refund";
    public static final String REFUSE_REFUND = "direct:refuse-refund";
    public static final String MANUAL_REFUND = "direct:manual-refund";
    public static final String UPDATE_TRAVEL = "direct:update-travel";

    public static final String OCR_IN = "direct:ocr-in";
    public static final String OCR_OUT = "direct:ocr-out";

    public static final String NOT_FOUND = "direct:not-found";
    public static final String GET_DESTINATION = "direct:get-destination";
}



