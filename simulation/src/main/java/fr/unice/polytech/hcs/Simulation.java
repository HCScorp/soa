package fr.unice.polytech.hcs;


import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Simulation implements Runnable {

    // TODO log everything

    private static final long MIN_SLEEP = 2000;
    private static final long MAX_SLEEP = 4000;

    private List<Flight> flights = new ArrayList<>();
    private List<Hotel> hotels = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();

    private String btrId;

    protected void pause() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextLong(MIN_SLEEP, MAX_SLEEP));
    }

    protected void searchFlights(FlightSearchRequest... fsrArray) throws UnirestException {
        for (FlightSearchRequest fsr : fsrArray) {
            flights.add(Telegram.searchFlight(fsr));
        }
    }

    protected void searchHotels(HotelSearchRequest... hsrArray) throws UnirestException {
        for (HotelSearchRequest hsr : hsrArray) {
            hotels.add(Telegram.searchHotel(hsr));
        }
    }

    protected void searchCars(CarSearchRequest... csrArray) throws UnirestException {
        for (CarSearchRequest csr : csrArray) {
            cars.add(Telegram.searchCar(csr));
        }
    }

    protected void sendBtr() throws UnirestException {
        btrId = Telegram.sendBtr(new BusinessTravelRequest(flights, hotels, cars));
    }

    protected void getBtr() throws UnirestException {
        Telegram.getBtr(btrId);
    }

    protected void approveBtr() throws UnirestException {
        Telegram.approveBtr(btrId);
    }
}
