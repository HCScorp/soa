package hotel.data.filter;

import hotel.data.exception.IllegalFilterValueException;
import org.bson.Document;

public interface FilterBuilder<T> {
    T buildFilter(Document bson) throws IllegalFilterValueException;
}