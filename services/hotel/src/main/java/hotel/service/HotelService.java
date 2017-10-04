package hotel.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.client.MongoCollection;
import hotel.data.DB;
import hotel.data.HotelQueryBuilder;
import hotel.data.HotelQueryHandler;
import hotel.data.Query;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Path("/hotel")
@Produces(MediaType.APPLICATION_JSON)
public class HotelService {

    private static final int INDENT_FACTOR = 2;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response search(String input) {
        try {
            // Parse JSON string to BSON
            Document searchCriterion = Document.parse(input);

            // Build search request from BSON
            Query query = HotelQueryBuilder.buildQuery(searchCriterion);

            // Evaluate search and get result
            HotelQueryHandler queryHandler = new HotelQueryHandler(query);

            // Get DB
            MongoCollection<Document> hotels = DB.getHotels();

            // Perform query
            List<Document> resultBson = queryHandler.performOn(hotels);

            //
            // Here we would convert Document to Hotel POJO using the right constructor if we had to
            //

            // Format output to JSON
            JSONArray result = new JSONArray(resultBson.stream().map(Document::toJson));

            // Build and send response with JSON
            return Response.ok().entity(result.toString(INDENT_FACTOR)).build();
        } catch (Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
    }

}
