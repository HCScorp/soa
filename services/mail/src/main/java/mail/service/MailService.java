package mail.service;

import org.json.JSONObject;
import mail.data.MailRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MailService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
	public Response sendMail(String input) {
        MailRequest r = new MailRequest(new JSONObject(input));

        if(r.getRecipient().equals("fail@mail.com")){
            return Response.ok().status(400).entity(new JSONObject().put("cause", "ko").toString()).build();
        }

        return Response.ok().status(200).entity(new JSONObject().put("cause", "ok").toString()).build();
	}
}
