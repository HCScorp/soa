package paf;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Flight {
    private String destination;
    private String begingTravel;
    private String endTravel;
    private CATEGORY category;


    @MongoObjectId
    int flightNumber;

    Flight() {
    }

    Flight(JSONObject jsonObject) {
        this.begingTravel = jsonObject.getString("beging");
        this.endTravel = jsonObject.getString("end");
        this.destination = jsonObject.getString("beging");
        this.category = CATEGORY.valueOf(jsonObject.getString("category"));
    }

    JSONObject toJson() {
        return new JSONObject().put("beging", this.begingTravel)
                .put("end", this.endTravel)
                .put("category", this.category.toString())
                .put("destination", this.destination);
    }


}