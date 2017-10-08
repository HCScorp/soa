package tcs.data;

import org.json.JSONObject;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class MailRequest {
    private String sender;
    private String recipient;
    private String object;
    private String message;


    public MailRequest(){};

    public MailRequest(JSONObject object){
        sender = object.getString("sender");
        recipient = object.getString("recipient");
        this.object = object.getString("object");
        message = object.getString("message");
    }


    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
