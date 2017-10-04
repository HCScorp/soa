package tcs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class MailRequest {
    private String sender;
    private String recipient;
    private String object;
    private String message;


    @XmlElement(name = "sender", required = true)
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    @XmlElement(name = "recipient", required = true)
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    @XmlElement(name = "object", required = true)
    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    @XmlElement(name = "message", required = true)
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
