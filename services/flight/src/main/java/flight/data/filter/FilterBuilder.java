package flight.data.filter;

import flight.data.exception.IllegalFilterValueException;
import org.bson.Document;

public interface FilterBuilder<T> {
    T buildFilter(Document bson) throws IllegalFilterValueException;
}