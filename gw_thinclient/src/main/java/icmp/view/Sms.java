/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icmp.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author benjaminsupanga
 */
@Entity
@Table(name = "sms", catalog = "icmp", schema = "")
@NamedQueries({
    @NamedQuery(name = "Sms.findAll", query = "SELECT s FROM Sms s"),
    @NamedQuery(name = "Sms.findBySmsId", query = "SELECT s FROM Sms s WHERE s.smsId = :smsId"),
    @NamedQuery(name = "Sms.findByToNumber", query = "SELECT s FROM Sms s WHERE s.toNumber = :toNumber"),
    @NamedQuery(name = "Sms.findByFromNumber", query = "SELECT s FROM Sms s WHERE s.fromNumber = :fromNumber"),
    @NamedQuery(name = "Sms.findByMessage", query = "SELECT s FROM Sms s WHERE s.message = :message"),
    @NamedQuery(name = "Sms.findBySubject", query = "SELECT s FROM Sms s WHERE s.subject = :subject"),
    @NamedQuery(name = "Sms.findByStatus", query = "SELECT s FROM Sms s WHERE s.status = :status"),
    @NamedQuery(name = "Sms.findByDateCreated", query = "SELECT s FROM Sms s WHERE s.dateCreated = :dateCreated"),
    @NamedQuery(name = "Sms.findByDateUpdated", query = "SELECT s FROM Sms s WHERE s.dateUpdated = :dateUpdated")})
public class Sms implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sms_id")
    private Short smsId;
    @Basic(optional = false)
    @Column(name = "to_number")
    private String toNumber;
    @Basic(optional = false)
    @Column(name = "from_number")
    private String fromNumber;
    @Basic(optional = false)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @Column(name = "subject")
    private String subject;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @Column(name = "date_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    public Sms() {
    }

    public Sms(Short smsId) {
        this.smsId = smsId;
    }

    public Sms(Short smsId, String toNumber, String fromNumber, String message, String subject, int status, Date dateCreated, Date dateUpdated) {
        this.smsId = smsId;
        this.toNumber = toNumber;
        this.fromNumber = fromNumber;
        this.message = message;
        this.subject = subject;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Short getSmsId() {
        return smsId;
    }

    public void setSmsId(Short smsId) {
        Short oldSmsId = this.smsId;
        this.smsId = smsId;
        changeSupport.firePropertyChange("smsId", oldSmsId, smsId);
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        String oldToNumber = this.toNumber;
        this.toNumber = toNumber;
        changeSupport.firePropertyChange("toNumber", oldToNumber, toNumber);
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        String oldFromNumber = this.fromNumber;
        this.fromNumber = fromNumber;
        changeSupport.firePropertyChange("fromNumber", oldFromNumber, fromNumber);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        String oldMessage = this.message;
        this.message = message;
        changeSupport.firePropertyChange("message", oldMessage, message);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        String oldSubject = this.subject;
        this.subject = subject;
        changeSupport.firePropertyChange("subject", oldSubject, subject);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        int oldStatus = this.status;
        this.status = status;
        changeSupport.firePropertyChange("status", oldStatus, status);
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        Date oldDateCreated = this.dateCreated;
        this.dateCreated = dateCreated;
        changeSupport.firePropertyChange("dateCreated", oldDateCreated, dateCreated);
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        Date oldDateUpdated = this.dateUpdated;
        this.dateUpdated = dateUpdated;
        changeSupport.firePropertyChange("dateUpdated", oldDateUpdated, dateUpdated);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (smsId != null ? smsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sms)) {
            return false;
        }
        Sms other = (Sms) object;
        if ((this.smsId == null && other.smsId != null) || (this.smsId != null && !this.smsId.equals(other.smsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "icmp.view.Sms[ smsId=" + smsId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
