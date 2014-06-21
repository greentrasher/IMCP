/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icmp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author benjaminsupanga
 */
public class SMS implements Serializable{
    private Short smsId;
    private String toNumber;
    private String fromNumber;
    private String message;
    private int status;
    private String subject;
    private Date dateCreated;
    private Date dateUpdated;

    public Short getSmsId() {
        return smsId;
    }

    public void setSmsId(Short smsId) {
        this.smsId = smsId;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
    
        
}
