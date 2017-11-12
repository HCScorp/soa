package fr.unice.polytech.hcs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger log = LogManager.getLogger("Main");

    public static void main(String[] args) throws IOException, UnirestException, InterruptedException {
        configureUnirest();

        // TODO multiple simulation
        Simulation s = new Simulation() {
            @Override
            public void run() {
                try {
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(s);
        t.start();
        t.join();

        /*

        // DONE
        L’employé fait sa recherche
        L’employé fait sa BTR et l'envoi au ws approver
        on récupère l'id de la BTR
        le manager accepte ou refuse via l'id du btr
        L’employé envoie ses factures une ou plusieurs a la fois

        // TODO
        Le manager peut voir l’avancé de son voyage (son statut, facture, etc.)
        L’employé déclare la fin de son voyage via un Web Service ce qui lance la demande de remboursement.
        Le système l’approuve automatiquement
        ou délègue l’approuval du refund au manager (on simule une notification par email)
          + l'employee doit envoyer une explanation pour son travel
          + le manager approuve ou refuse sa demande de remboursement
        Une fois le refund approuvé, la procédure d’archivage est lancé
         */
        Unirest.shutdown();
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
