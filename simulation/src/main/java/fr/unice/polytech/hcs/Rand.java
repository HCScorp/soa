package fr.unice.polytech.hcs;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Rand {

    private static final Random RANDOM = new Random();

    public static final List<FlightSearchRequest> fsrsGo = Arrays.asList(
            new FlightSearchRequest("Nice", "Paris", "2017-08-14", "10:00:00", "18:30:00", "DIRECT", 120, "ECO", "EasyJet", "ASCENDING"),
            new FlightSearchRequest("Nice", "Paris", "2017-08-15", "08:00:00", "12:00:00", "DIRECT", 200, "ECO", "EasyJet", "ASCENDING")
    );

    public static final List<FlightSearchRequest> fsrsBack = Arrays.asList(
            new FlightSearchRequest("Paris", "Nice", "2017-08-25", "07:00:00", "19:30:00", "DIRECT", 140, "ECO", "EasyJet", "ASCENDING"),
            new FlightSearchRequest("Paris", "Nice", "2017-08-21", "09:00:00", "14:45:00", "DIRECT", 220, "ECO", "EasyJet", "ASCENDING")
    );

    public static final List<HotelSearchRequest> hsrs = Arrays.asList(
            new HotelSearchRequest("Paris", "2017-08-21", "2017-08-25", "ASCENDING"),
            new HotelSearchRequest("Paris", "2017-08-21", "2017-08-25", "ASCENDING")
    );

    public static final List<CarSearchRequest> csrs = Arrays.asList(
            new CarSearchRequest("Paris", "2017-08-16", "2017-08-20"),
            new CarSearchRequest("Paris", "2017-08-17", "2017-08-19")
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
}
