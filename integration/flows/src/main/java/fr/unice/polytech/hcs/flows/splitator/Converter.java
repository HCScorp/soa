package fr.unice.polytech.hcs.flows.splitator;


public interface Converter<In, Out> {
    Out convert(In in);
}
