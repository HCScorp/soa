package fr.unice.polytech.hcs.flows.utils;

import java.util.HashMap;
import java.util.Map;

public class Build {
    public static <K, V> Entry<K, V> entry(K key, V value) {
        return new Entry<>(key, value);
    }

    public static <K, V> Map<K, V> map(Entry<K, V>... entries) {
        Map<K, V> result = new HashMap<>();
        for (Entry<K, V> e : entries) {
            result.put(e.key, e.value);
        }
        return result;
    }
}
