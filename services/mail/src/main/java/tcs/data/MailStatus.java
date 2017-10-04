package tcs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class MailStatus {

    private int code;
    private String cause;

    @XmlElement(name = "code", required = true)
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    @XmlElement(name = "cause", required = true)
    public String getCause() { return cause; }
    public void setCause(String cause) { this.cause = cause; }
}
