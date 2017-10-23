package fr.unice.polytech.hcs.flows.splitator;


public interface Convertor<In, Out> {
    Out convert(In in);
}
