package service;

import data.BusinessTravelRequest;
import data.BusinessTravelRequestStatus;
import data.database.BTRHandler;
import data.database.DB;
import data.database.exception.BTRNotFound;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btr")
@Produces(MediaType.APPLICATION_JSON)
public class ApproverService {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewBTR(String input) {
        BTRHandler.insert(Document.parse(input));
        return Response.status(200).build();
    }

    @Path("/{id}")
    @GET
    public Response getBTR(@PathParam("id") int id){
        BusinessTravelRequest btr = null;

        try {
            btr = BTRHandler.get(id);
        } catch (BTRNotFound btrNotFound) {
            JSONObject resp = new JSONObject();
            resp.put("Cause", btrNotFound.cause());
            return Response.status(404).entity(resp.toString()).build();
        }

        return Response.ok().entity(btr.toJSON().toString()).build();
    }

    @Path("/{id}")
    @PUT
    public Response changeStatus(@PathParam("id") int id, String status){
        BusinessTravelRequestStatus s = BusinessTravelRequestStatus.valueOf(status);

        try {
            BTRHandler.update(id, "status", s.toString());
        } catch (BTRNotFound btrNotFound) {
            JSONObject resp = new JSONObject();
            resp.put("Cause", btrNotFound.cause());
            return Response.status(404).entity(resp.toString()).build();
        }

        return Response.status(200).build();
    }
}