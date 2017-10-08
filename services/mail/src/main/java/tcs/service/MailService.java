package tcs.service;

import tcs.data.MailRequest;
import tcs.data.MailStatus;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;


@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/cookbook/",
		    portName          = "MailPort",
		    serviceName       = "MailService",
		    endpointInterface = "tcs.service.MailService")
@Produces(MediaType.APPLICATION_JSON)
public class MailImpl implements MailService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
	public Response send(String input) {
		MailStatus status = new MailStatus();
        status.setCause("Ok");

        return Response.ok().status(200).;
	}
}
