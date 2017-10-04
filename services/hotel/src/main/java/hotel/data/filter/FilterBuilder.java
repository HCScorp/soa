package hotel.data.filter;

import org.bson.Document;

public interface FilterBuilder<T> {
    T buildFilter(Document bson);
}