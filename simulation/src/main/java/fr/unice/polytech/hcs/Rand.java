package fr.unice.polytech.hcs;

import fr.unice.polytech.hcs.pojo.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Rand {

    private static final Random RANDOM = new Random();

    ////////////////////
    ////// Search //////
    ////////////////////

    // Flight
    public static final List<FlightSearchRequest> fsrsGo = Arrays.asList(
            new FlightSearchRequest("Nice", "Paris", "2017-08-14", "10:00:00", "18:30:00", "DIRECT", 120, "ECO", "EasyJet", "ASCENDING"),
            new FlightSearchRequest("Nice", "Paris", "2017-08-15", "08:00:00", "15:50:00", "DIRECT", 200, "ECO", "EasyJet", "ASCENDING")
    );

    public static final List<FlightSearchRequest> fsrsBack = Arrays.asList(
            new FlightSearchRequest("Paris", "Nice", "2017-08-25", "07:00:00", "19:30:00", "DIRECT", 140, "ECO", "EasyJet", "ASCENDING"),
            new FlightSearchRequest("Paris", "Nice", "2017-08-21", "09:00:00", "14:45:00", "DIRECT", 220, "ECO", "EasyJet", "ASCENDING")
    );

    // Hotel
    public static final List<HotelSearchRequest> hsrs = Arrays.asList(
            new HotelSearchRequest("Paris", "2017-08-21", "2017-08-25", "ASCENDING"),
            new HotelSearchRequest("Paris", "2017-08-21", "2017-08-25", "ASCENDING")
    );

    // Car
    public static final List<CarSearchRequest> csrs = Arrays.asList(
            new CarSearchRequest("Paris", "2017-08-16", "2017-08-20"),
            new CarSearchRequest("Paris", "2017-08-17", "2017-08-19")
    );


    /////////////////////
    ////// Expense //////
    /////////////////////

    // Flight
    public static final List<Expense> expFlights = Arrays.asList(
            new Expense("Flight", "byte_array_representing_image", 95.5),
            new Expense("Flight", "byte_array_representing_image", 3450.99),
            new Expense("Flight", "byte_array_representing_image", 88.4),
            new Expense("Flight", "byte_array_representing_image", 145.3)
    );

    // Hotel
    public static final List<Expense> expHotels = Arrays.asList(
            new Expense("Hotel", "byte_array_representing_image", 25.2),
            new Expense("Hotel", "byte_array_representing_image", 30.1)
    );

    // Car
    public static final List<Expense> expCars = Arrays.asList(
            new Expense("Car", "byte_array_representing_image", 50.2),
            new Expense("Car", "byte_array_representing_image", 35.9)
    );

    // Other
    public static final List<Expense> expOthers = Arrays.asList(
            new Expense("Resto", "byte_array_representing_image", 18.2),
            new Expense("Casino", "byte_array_representing_image", 148668.2),
            new Expense("Metro", "byte_array_representing_image", 5.1)
    );


    public static FlightSearchRequest fsrGo() {
        return fsrsGo.get(RANDOM.nextInt(fsrsGo.size()));
    }

    public static FlightSearchRequest fsrBack() {
        return fsrsBack.get(RANDOM.nextInt(fsrsBack.size()));
    }

    public static HotelSearchRequest hsr() {
        return hsrs.get(RANDOM.nextInt(hsrs.size()));
    }

    public static CarSearchRequest csr() {
        return csrs.get(RANDOM.nextInt(csrs.size()));
    }

    public static Expense expFlight() {
        return expFlights.get(RANDOM.nextInt(expFlights.size()));
    }

    public static Expense expHotel() {
        return expHotels.get(RANDOM.nextInt(expHotels.size()));
    }

    public static Expense expCar() {
        return expCars.get(RANDOM.nextInt(expCars.size()));
    }

    public static Expense expOther() {
        return expOthers.get(RANDOM.nextInt(expOthers.size()));
    }
}
