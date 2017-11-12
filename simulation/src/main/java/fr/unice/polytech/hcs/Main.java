package fr.unice.polytech.hcs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import fr.unice.polytech.hcs.pojo.RefundStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final Logger log = LogManager.getLogger("Main");

    public static void main(String[] args) throws IOException, UnirestException, InterruptedException {
        configureUnirest();

        log.info("Calm before the STORM");
        Thread.sleep(5000);

        log.info("Waiting for the bus to be up and running..");
        Telegram.waitForBus();
        log.info("Bus ready, starting simulation..");

        Simulation s = new Simulation() {
            @Override
            public void run() {
                try {
                    pause();

                    searchFlights(Rand.fsrGo(), Rand.fsrBack());
                    pause();

                    searchHotels(Rand.hsr());
                    pause();

                    searchCars(Rand.csr());
                    pause();

                    sendBtr();
                    pause();

                    getBtr();
                    pause();

                    approveBtr();
                    pause();

                    sendExpenses(
                            Rand.expFlight(), Rand.expFlight(),
                            Rand.expHotel(),
                            Rand.expCar(),
                            Rand.expOther(), Rand.expOther()
                    );
                    pause();

                    monitorTravel();
                    pause();

                    RefundStatus status = endTravel();
                    pause();

                    // If refund ok, the goodbye
                    if("ok".equalsIgnoreCase(status.status)) {
                        return;
                    }

                    sendExplanation();
                    pause();

                    if (new Random().nextBoolean()) {
                        approveRefund();
                    } else {
                        denyRefund();
                    }

                    monitorTravel();
                    pause();

                } catch (Exception e) {
                    log.error("Something went wrong, but do not worry, the light will save us.");
                }
            }
        };

        for(int i = 0; i < 4; i++) {
            batch(4, s);
        }

        Unirest.shutdown();
    }

    private static void batch(int bSize, Simulation s) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < bSize; i++) {
            Thread t = new Thread(s);
            t.start();
            threads.add(t);
        }

        for(Thread t : threads) {
            t.join();
        }
    }

    private static void configureUnirest() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Unirest.setDefaultHeader("accept", "application/json");
        Unirest.setDefaultHeader("Content-Type", "application/json");
    }
}
