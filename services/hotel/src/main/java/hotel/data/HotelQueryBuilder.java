package hotel.data;

import hotel.data.filter.FilterBuilder;
import hotel.data.sorter.SorterBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.orderBy;

// Builder in the name doesn't mean it uses a Builder pattern.
public class HotelQueryBuilder {

    private static final Collection<FilterBuilder<Bson>> FILTER_BUILDERS = new ArrayList<>();
    private static final Collection<SorterBuilder<Bson>> SORTER_BUILDERS = new ArrayList<>();

    public static void addFilter(FilterBuilder<Bson> filter) {
        FILTER_BUILDERS.add(filter);
    }

    public static void addSorter(SorterBuilder<Bson> sorter) {
        SORTER_BUILDERS.add(sorter);
    }

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
