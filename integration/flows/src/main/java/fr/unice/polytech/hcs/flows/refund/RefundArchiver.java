package fr.unice.polytech.hcs.flows.refund;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.hcs.flows.expense.Travel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static fr.unice.polytech.hcs.flows.utils.Endpoints.REFUND_SENDING;

public class RefundArchiver extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        // In progress
        from(REFUND_SENDING)
                .routeDescription("where to send your refund piece")
                .routeId("refund-piece-route")

                .process(exchange -> {
                    System.out.println("exchange ! " + exchange.getIn().getBody());
                    Travel travel =  exchange.getIn().getBody(Travel.class);
                    System.out.println("travel : " + travel);

                    exchange.getIn().setHeader("id", Integer.toString(travel.travelId));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);

                    oos.writeObject(travel);
                    oos.flush();
                    oos.close();

                    InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
                    exchange.getIn().setBody(inputStream);

                })
                .toD("ftp://fttp:11021/${header.id}?username=test&password=test&passiveMode=true")
                .log("file sended to the ftp server !");
    }
}
