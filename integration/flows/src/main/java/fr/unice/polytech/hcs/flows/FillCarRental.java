package fr.unice.polytech.hcs.flows;

import fr.unice.polytech.hcs.flows.data.Car;
import fr.unice.polytech.hcs.flows.utils.CsvFormat;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.bson.Document;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FillCarRental extends RouteBuilder {


    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);


    @Override
    public void configure() throws Exception {

        from("file:/servicemix/camel/input?fileName=car.csv")
                .routeId("csv-to-car-rental")
                .routeDescription("Loads a CSV file containing car and routes contents to  Rental")
                .log("processing ${file:name}")
                .unmarshal(CsvFormat.buildCsvFormat())
                .log("Splitting content")
                .split(body())
                .parallelProcessing().executorService(WORKERS)
                .log("Transforming a CSV line into a Person")
                // change that "thing"
                .process((Exchange exchange) -> {
                    Map<String, Object> v = (Map<String, Object>) exchange.getIn().getBody();
                    System.out.println("map casted : " + v);
                    System.out.println("body : " + exchange.getIn().getBody());
                } )
                .to("activemq:car");


        from("activemq:car")
                .routeId("request-to-car-ws")
                .routeDescription("Send the car request to the car WS")
                .log("create request for car WS ")
                .process((Exchange exchange) -> {
                    Car req = (Car) exchange.getIn().getBody();
                } )
                .log("${body.bookedDate} Marshalling request")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .log("Marshalling into a JSON body")
                .marshal().json(JsonLibrary.Gson)
                .to("http:car:8080/flight-service-document/car");
        }
}
