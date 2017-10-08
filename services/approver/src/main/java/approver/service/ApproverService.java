package approver.service;

import approver.data.BusinessTravelRequest;
import approver.data.database.BTRHandler;
import approver.data.database.exception.BTRNotFound;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btr")
@Produces(MediaType.APPLICATION_JSON)
public class ApproverService {

    private static final JSONObject INVALID_ID = new JSONObject().put("error", "invalid id formatting");
    private static final JSONObject UNKNOWN_ACTION = new JSONObject().put("error", "unknown action");

    private final BTRHandler btrHandler;

    public ApproverService() {
        this.btrHandler = new BTRHandler();
    }

    public ApproverService(BTRHandler btrHandler) {
        this.btrHandler = btrHandler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBTR(String input) {
        try {
            BusinessTravelRequest btr = BusinessTravelRequest.fromExternalJson(input);
            ObjectId id = btrHandler.insert(btr);
            JSONObject result = new JSONObject().put("_id", id.toHexString());
            return Response.status(200).entity(result.toString()).build();
        } catch (JSONException e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
    }

    @Path("/{id}")
    @GET
    public Response getBTR(@PathParam("id") String idStr) {
        if (!ObjectId.isValid(idStr)) {
            return Response.status(404).entity(INVALID_ID.toString()).build();
        }

        ObjectId id = new ObjectId(idStr);

        try {
            BusinessTravelRequest btr = btrHandler.getBTR(id);
            return Response.ok().entity(btr.toBSON().toJson()).build();
        } catch (BTRNotFound e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(404).entity(error.toString()).build();
        }
    }

    @Path("/{id}")
    @PUT
    public Response updateBTR(@PathParam("id") String idStr, @QueryParam("action") String action) {
        if (!ObjectId.isValid(idStr)) {
            return Response.status(404).entity(INVALID_ID.toString()).build();
        }

        if (!("APPROVE".equalsIgnoreCase(action) || "DENY".equalsIgnoreCase(action))) {
            return Response.status(400).entity(UNKNOWN_ACTION.toString()).build();
        }

        ObjectId id = new ObjectId(idStr);

        try {
            BusinessTravelRequest.Status status = BusinessTravelRequest.Status.WAITING;

            if ("APPROVE".equalsIgnoreCase(action)) {
                status = BusinessTravelRequest.Status.APPROVED;
            } else if ("DENY".equalsIgnoreCase(action)) {
                status = BusinessTravelRequest.Status.DENIED;
            }

            btrHandler.update(id, "status", status.toString());
        } catch (BTRNotFound e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(404).entity(error.toString()).build();
        }

        return Response.status(200).build();
    }
}