package hotel.data.sorter;

import org.bson.Document;

public interface SorterBuilder<T> {
    T buildSorter(Document bson);

}
