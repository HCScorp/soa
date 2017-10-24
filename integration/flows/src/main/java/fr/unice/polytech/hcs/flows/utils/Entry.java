package fr.unice.polytech.hcs.flows.utils;

public class Entry<K, V> {
    public final K key;
    public final V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
