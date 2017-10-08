package approver.data.database.exception;

import org.bson.types.ObjectId;

public class BTRNotFound extends Exception {
    public BTRNotFound(ObjectId id) {
        super("Business Travel Request #" + id.toHexString() + " not found in database");
    }
}