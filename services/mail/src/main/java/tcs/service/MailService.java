package tcs.service;

import tcs.data.MailRequest;
import tcs.data.MailStatus;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="Mail", targetNamespace = "http://informatique.polytech.unice.fr/soa1/cookbook/")
public interface MailService {

	@WebResult(name="status")
    MailStatus Send(@WebParam(name="mailRequest") MailRequest request);
}

