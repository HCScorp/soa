package scenarios;

import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.JSONObject;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.StringReader;

public class MailStepDefinition {
    private String host;
    private int port;
    private JSONObject mailRequest;

    @Given("^The Mail service deployed on (.*):(\\d+)$")
    public void init(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a mail from (.*) to (.*)$")
    public void setUpMail(String from, String to) {
        mailRequest = new JSONObject();
        mailRequest.put("sender", from);
        mailRequest.put("recipient", to);
    }


    @When("^the mail is sent$")
    public void theMailIsSent() throws Throwable {
        JsonReader reader = Json.createReader(new StringReader(mailRequest.toString()));
        JsonStructure object = reader.readObject();
        reader.close();

        new ApacheRequest("http://" + host + ":" + port + "/mail-service-rpc/")
                .method(Request.POST).body().set(object.toString()).back().
                header("Content-Type", "application/json").fetch();
    }
}
