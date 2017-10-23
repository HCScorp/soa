package fr.unice.polytech.hcs.flows.splitator;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplittatorRoute< In extends Serializable,
                                Mid extends Iterable<Out>,
                                Out extends SerializableComparable<Out> >
        extends RouteBuilder {

    private final String name;
    private final String restEndpoint;
    private final Class<In> inClass;
    private final Class<Mid> midClass;
    private final Class<Out> outClass;
    private final String unmarshalUri;
    private final String multicastUri;
    private final AggregationStrategy aggregationStrategy;
    private final Collection<String> targets;
    private final ExecutorService workers;
    private final long timeout;
    private final String routeId;
    private final String routeDescription;

    protected SplittatorRoute(String restEndpoint,
                            Class<In> inClass, Class<Mid> midClass, Class<Out> outClass,
                            AggregationStrategy aggregationStrategy,
                            String unmarshalUri, String multicastUri,
                            Collection<String> targets,
                            int threadPoolSize, long timeout,
                            String routeId, String routeDescription) {
        this.name = inClass.getSimpleName() + "__" + outClass.getSimpleName();
        this.restEndpoint = restEndpoint;
        this.inClass = inClass;
        this.midClass = midClass;
        this.outClass = outClass;
        this.unmarshalUri = unmarshalUri;
        this.multicastUri = multicastUri;
        this.aggregationStrategy = aggregationStrategy;
        this.targets = targets;
        this.workers = Executors.newFixedThreadPool(threadPoolSize);
        this.timeout = timeout;
        this.routeId = routeId == null ? name : routeId;
        this.routeDescription = routeDescription == null ? name : routeDescription;
    }

    public String getName() {
        return name;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
        ;

        rest(restEndpoint)
                .post()
                .to(unmarshalUri)
        ;

        from(unmarshalUri)
                .routeId(routeId)
                .routeDescription(routeDescription)

                .log("Unmarshal request from JSON to request")
                .unmarshal().json(JsonLibrary.Jackson, inClass)

                .log("Send request to message queue")
                .to(multicastUri)
        ;

        from(multicastUri)
                .log("Multicast request to the services")
                    .multicast(aggregationStrategy)
                    .parallelAggregate()
                    .executorService(workers)
                    .parallelProcessing()
                    .timeout(timeout)
                    .inOut(targets.toArray(new String[targets.size()]))

                .log("Marshal response to JSON")
                .marshal().json(JsonLibrary.Jackson, outClass)
        ;
    }
}
