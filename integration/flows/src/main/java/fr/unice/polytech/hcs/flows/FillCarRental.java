package fr.unice.polytech.hcs.flows;

import org.apache.camel.builder.RouteBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FillCarRental extends RouteBuilder {


    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);


    @Override
    public void configure() throws Exception {

        // from("file:/file:/servicemix/camel/input?fileName=car.csv)

        }
}
