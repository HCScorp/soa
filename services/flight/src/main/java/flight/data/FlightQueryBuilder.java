package flight.data;

import flight.data.filter.*;
import flight.data.sorter.PriceOrderFB;
import flight.data.sorter.SorterBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Sorts.orderBy;

// Builder in the name doesn't mean it uses a Builder pattern.
public class FlightQueryBuilder {

    private static final Collection<FilterBuilder<Bson>> FILTER_BUILDERS =
            Arrays.asList(
                    new DateFB(),
                    new DestinationFB(),
                    new JourneyTypeFB(),
                    new MaxTravelTimeFB(),
                    new OriginFB(),
                    new CategoryFB(),
                    new AirlineFB());

    private static final Collection<SorterBuilder<Bson>> SORTER_BUILDERS =
            Arrays.asList(
                    new PriceOrderFB());

    public static Query buildQuery(final Document searchCriterion) {
        Query query = new Query();

        query.filter = and(FILTER_BUILDERS.stream()
                .map(fb -> fb.buildFilter(searchCriterion))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        query.sorter = orderBy(SORTER_BUILDERS.stream()
                .map(fb -> fb.buildSorter(searchCriterion))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        return query;
    }
}
