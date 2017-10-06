package flight.data.sorter;

import flight.data.exception.IllegalSorterValueException;
import org.bson.Document;

public interface SorterBuilder<T> {
    T buildSorter(Document bson) throws IllegalSorterValueException;

}
