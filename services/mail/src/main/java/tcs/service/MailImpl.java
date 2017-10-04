package tcs.service;

import tcs.data.MailRequest;
import tcs.data.MailStatus;

import javax.jws.WebService;


@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/cookbook/",
		    portName          = "ExternalTaxComputerPort",
		    serviceName       = "ExternalTaxComputerService",
		    endpointInterface = "tcs.service.MailService")
public class MailImpl implements MailService {

	public MailStatus Send(MailRequest request) {
        // some mail to send

        MailStatus status = new MailStatus();
        status.setCode(200);
        status.setCause("Ok");

        return status;
	}

}
