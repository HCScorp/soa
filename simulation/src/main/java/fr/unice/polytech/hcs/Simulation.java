package fr.unice.polytech.hcs;


import com.mashape.unirest.http.exceptions.UnirestException;
import fr.unice.polytech.hcs.pojo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Simulation implements Runnable {

    private static final Logger log = LogManager.getLogger("Simulation");

    private static final long MIN_SLEEP = 4000;
    private static final long MAX_SLEEP = 8000;

    private List<Flight> flights = new ArrayList<>();
    private List<Hotel> hotels = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();

    private String btrId;

    protected void pause() throws InterruptedException {
        long duration = ThreadLocalRandom.current().nextLong(MIN_SLEEP, MAX_SLEEP);
        log.info("Pausing for " + duration + "ms");
        Thread.sleep(duration);
    }

    protected void searchFlights(FlightSearchRequest... fsrArray) throws UnirestException {
        for (FlightSearchRequest fsr : fsrArray) {
            log.info("Searching for flights " + fsr);
            Flight f = Telegram.searchFlight(fsr);
            log.info("Get flight search response and add to BTR " + f);
            flights.add(f);
        }
    }

    protected void searchHotels(HotelSearchRequest... hsrArray) throws UnirestException {
        for (HotelSearchRequest hsr : hsrArray) {
            log.info("Searching for hotels " + hsr);
            Hotel h = Telegram.searchHotel(hsr);
            log.info("Get hotel search response and add to BTR " + h);
            hotels.add(h);
        }
    }

    protected void searchCars(CarSearchRequest... csrArray) throws UnirestException {
        for (CarSearchRequest csr : csrArray) {
            log.info("Searching for cars " + csr);
            Car c = Telegram.searchCar(csr);
            log.info("Get car search response and add to BTR " + c);
            cars.add(c);
        }
    }

    protected void sendBtr() throws UnirestException {
        log.info("Sending BTR to approval");
        btrId = Telegram.sendBtr(new BusinessTravelRequest(flights, hotels, cars));
        log.info("Received BTR id: " + btrId);
    }

    protected void getBtr() throws UnirestException {
        log.info("Manager is requesting BTR");
        BusinessTravelRequest btr = Telegram.getBtr(btrId);
        log.info("Get BTR from approver: " + btr);
    }

    protected void approveBtr() throws UnirestException {
        log.info("Manager is approving BTR " + btrId);
        Telegram.approveBtr(btrId);
    }

    protected void sendExpenses(Expense ...expenses) throws IOException {
        log.info("Building expense report");
        ExpenseReport expReport = new ExpenseReport(btrId, Arrays.asList(expenses));

        log.info("Sending expense report " + expReport);
        Telegram.sendExpenseReport(expReport);
    }

    protected void monitorTravel() throws UnirestException {
        log.info("Manager is monitoring the business travel");
        Travel t = Telegram.getTravel(btrId);
        log.info("Manager consulted " + t);
    }

    protected RefundStatus endTravel() throws UnirestException {
        log.info("Ending travel");
        RefundStatus status =  Telegram.endTravel(btrId);
        log.info("Refund status: " + status);
        return status;
    }

    protected void sendExplanation() throws UnirestException {
        Explanation e = new Explanation("no reason", btrId);
        log.info("Sending explanation: " + e);
        Telegram.sendExplanation(e);
    }

    protected void approveRefund() throws UnirestException {
        log.info("Manager is approving refund " + btrId);
        ExplanationAnswer e = new ExplanationAnswer(btrId, true);
        Telegram.sendExplanationAnswer(e);
    }

    protected void denyRefund() throws UnirestException {
        log.info("Manager is denying refund " + btrId);
        ExplanationAnswer e = new ExplanationAnswer(btrId, false);
        Telegram.sendExplanationAnswer(e);
    }
 }
