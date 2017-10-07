package car.data;

import car.data.filter.DurationFB;
import car.data.filter.PlaceFB;
import car.data.filter.FilterBuilder;
import car.data.sorter.SorterBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.orderBy;

// Builder in the name doesn't mean it uses a Builder pattern.
public class CarQueryBuilder {

    private static final Collection<FilterBuilder<Bson>> FILTER_BUILDERS =
            Arrays.asList(
                    new DurationFB(),
                    new PlaceFB());

    private static final Collection<SorterBuilder<Bson>> SORTER_BUILDERS =
            Collections.emptyList();

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
