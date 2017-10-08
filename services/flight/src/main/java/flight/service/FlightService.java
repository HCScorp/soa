package flight.service;

import com.mongodb.client.MongoCollection;
import flight.data.DB;
import flight.data.FlightQueryBuilder;
import flight.data.FlightQueryHandler;
import flight.data.Query;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/flight")
@Produces(MediaType.APPLICATION_JSON)
public class FlightService {

    private static final int INDENT_FACTOR = 2;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response search(String input) {
        try {
            // Parse JSON string to BSON
            Document searchCriterion = Document.parse(input);

            // Build search request from BSON
            Query query = FlightQueryBuilder.buildQuery(searchCriterion);

            // Evaluate search and get result
            FlightQueryHandler queryHandler = new FlightQueryHandler(query);

            // Get DB
            MongoCollection<Document> hotels = DB.getFlights();

            // Perform query
            List<Document> resultBson = queryHandler.performOn(hotels);

            //
            // Here we would convert Document to Flight POJO using the right constructor if we had to
            //

            // Format output to JSON
            JSONObject result = new JSONObject();
            result.put("flights", new JSONArray(resultBson.stream().map(Document::toJson).toArray()));

            // Build and send response with JSON
            return Response.ok().entity(result.toString(INDENT_FACTOR)).build();
        } catch (Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
    }

}
