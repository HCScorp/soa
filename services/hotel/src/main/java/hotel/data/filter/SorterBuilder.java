package hotel.data.filter;

import org.bson.Document;

public interface SorterBuilder<T> {
    T buildSorter(Document bson);

}
