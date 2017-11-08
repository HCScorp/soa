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
            e2.getIn().setBody(cheapestList(null, e2.getIn().getBody(inClass)));
            return e2;
        }

        Out cheapest;

        if (outClass.isInstance(e1.getIn().getBody())) {
            cheapest = cheapest(e1.getIn().getBody(outClass), e2.getIn().getBody(inClass));
        } else {
            cheapest = cheapestList(e1.getIn().getBody(inClass), e2.getIn().getBody(inClass));
        }

        e2.getIn().setBody(cheapest);

        return e2;
    }

    private Out cheapestList(In r1, In r2) {
        Out cheapest = null;

        if(r1 != null) {
            for (Out p : r1) {
                if (cheapest == null || p.compareTo(cheapest) < 0) {
                    cheapest = p;
                }
            }
        }

        if(r2 != null) {
            for (Out p : r2) {
                if (cheapest == null || p.compareTo(cheapest) < 0) {
                    cheapest = p;
                }
            }
        }

        return cheapest;
    }

    private Out cheapest(Out o1, In r) {
        Out cheapest = o1;

        if(r != null) {
            for (Out p : r) {
                if (cheapest == null || p.compareTo(cheapest) < 0) {
                    cheapest = p;
                }
            }
        }

        return cheapest;
    }
}
