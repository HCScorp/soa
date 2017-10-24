package fr.unice.polytech.hcs.flows.utils;

public class Cast {
    public static Double toDouble(Object o) {
        if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof Integer) {
            return ((Integer) o).doubleValue();
        }
        return null;
    }
}
