package fr.unice.polytech.hcs.flows.splitator;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class CheapestAggregator<In extends Iterable<Out>, Out extends SerializableComparable<Out>> implements AggregationStrategy {

    private final Class<In> inClass;
    private final Class<Out> outClass;

    public CheapestAggregator(Class<In> inClass, Class<Out> outClass) {
        this.inClass = inClass;
        this.outClass = outClass;
    }

    @Override
    public Exchange aggregate(Exchange e1, Exchange e2) {
        if (e1 == null) {
            return e2;
        }

        Out cheapest = null;

        if(outClass.isInstance(e1.getIn().getBody())) {
            cheapest = e1.getIn().getBody(outClass);
        } else {
            Iterable<Out> fsr1 = e1.getIn().getBody(inClass);
            for(Out p : fsr1) {
                if(cheapest == null || p.compareTo(cheapest) < 0) {
                    cheapest = p;
                }
            }
        }

        Iterable<Out> fsr2 = e2.getIn().getBody(inClass);
        for(Out p : fsr2) {
            if(cheapest == null || p.compareTo(cheapest) < 0) {
                cheapest = p;
            }
        }

        e2.getIn().setBody(cheapest);

        return e2;
    }
}
