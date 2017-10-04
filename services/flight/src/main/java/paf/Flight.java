package paf;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Flight {
    private String destination;
    private String beging;
    private String end;
    private CATEGORY category;


    @MongoObjectId
    int flightNumber;

    Flight() {
    }

    Flight(JSONObject jsonObject) {
        this.beging = jsonObject.getString("beging");
        this.end = jsonObject.getString("end");
        this.destination = jsonObject.getString("beging");
        this.category = CATEGORY.valueOf(jsonObject.getString("category"));
    }

    JSONObject toJson() {
        return new JSONObject().put("beging", this.beging)
                .put("end", this.end)
                .put("category", this.category.toString())
                .put("destination", this.destination);
    }


}