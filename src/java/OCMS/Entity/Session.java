/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findAllSession",query="select s from Session s"),
    @NamedQuery(name="findUpcommingSession",query="select s from Session s where s.sessionDateTime>:currentDateTime"),
    @NamedQuery(name="findSessionByName", query="select s from Session s where s.sessionName=:sSessionName"),
    @NamedQuery(name="findSessionByTimeZone", query="select s from Session s where s.timeZone=:sTimeZone"),
    @NamedQuery(name="findSessionById", query="select s from Session s where s.sessionId=:sSessionId")
    
})
public class Session implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;
    @Column(nullable = false)
    private String sessionName;
    private String timeZone;
    @Column(nullable = false)
    private Date sessionDateTime;
    private String organization;
    private String country;
    private boolean isSeatAvailable;
    private int maximumParticipant;
    private Date createdDate;
    private Date updatedDate;
    @OneToMany(mappedBy = "sessionId")
    private List<SessionPaper> sessionPapers;
    @OneToMany(mappedBy = "sessionId")
    private List<SessionParticipant> sessionParticipants;

    public Session() {
    }

    public Session(Long sessionId, String sessionName, String timeZone, Date sessionDateTime, String organization, String country, boolean isSeatAvailable, int maximumParticipant, Date createdDate, Date updatedDate) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.timeZone = timeZone;
        this.sessionDateTime = sessionDateTime;
        this.organization = organization;
        this.country = country;
        this.isSeatAvailable = isSeatAvailable;
        this.maximumParticipant = maximumParticipant;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Session(Long sessionId, String sessionName, String timeZone, Date sessionDateTime, String organization, String country, boolean isSeatAvailable, int maximumParticipant, Date createdDate, Date updatedDate, List<SessionPaper> sessionPapers, List<SessionParticipant> sessionParticipants) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.timeZone = timeZone;
        this.sessionDateTime = sessionDateTime;
        this.organization = organization;
        this.country = country;
        this.isSeatAvailable = isSeatAvailable;
        this.maximumParticipant = maximumParticipant;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.sessionPapers = sessionPapers;
        this.sessionParticipants = sessionParticipants;
    }
    

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Date getSessionDateTime() {
        return sessionDateTime;
    }

    public void setSessionDateTime(Date sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isIsSeatAvailable() {
        return isSeatAvailable;
    }

    public void setIsSeatAvailable(boolean isSeatAvailable) {
        this.isSeatAvailable = isSeatAvailable;
    }

    public int getMaximumParticipant() {
        return maximumParticipant;
    }

    public void setMaximumParticipant(int maximumParticipant) {
        this.maximumParticipant = maximumParticipant;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<SessionPaper> getSessionPapers() {
        return sessionPapers;
    }

    public void setSessionPapers(List<SessionPaper> sessionPapers) {
        this.sessionPapers = sessionPapers;
    }

    public List<SessionParticipant> getSessionParticipants() {
        return sessionParticipants;
    }

    public void setSessionParticipants(List<SessionParticipant> sessionParticipants) {
        this.sessionParticipants = sessionParticipants;
    }

    @Override
    public String toString() {
        return "Session{" + "sessionId=" + sessionId + ", sessionName=" + sessionName + ", timeZone=" + timeZone + ", sessionDateTime=" + sessionDateTime + ", organization=" + organization + ", country=" + country + ", isSeatAvailable=" + isSeatAvailable + ", maximumParticipant=" + maximumParticipant + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + '}';
    }
    
    
}
