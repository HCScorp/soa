package car.data;

import org.bson.conversions.Bson;

public class Query {
    public Bson filter;
    public Bson sorter;

    public Query() {
    }

    public Query(Bson filter, Bson sorter) {
        this.filter = filter;
        this.sorter = sorter;
    }
}
