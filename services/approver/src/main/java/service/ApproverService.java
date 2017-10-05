package service;

import data.BusinessTravelRequest;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/btr")
@Produces(MediaType.APPLICATION_JSON)
public class ApproverService {

    // May be useful to return a status (search how to return specific http code)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createNewBTR(JSONObject btr) {
        //insert btr in database
    }

    @Path("/{id}")
    @GET
    public BusinessTravelRequest getBTR(@PathParam("id") int id){
        // search in database for the btr with the id given
        return new BusinessTravelRequest();
    }

    @Path("/{id}")
    @PUT
    public void changeStatus(@PathParam("id") int id)
}