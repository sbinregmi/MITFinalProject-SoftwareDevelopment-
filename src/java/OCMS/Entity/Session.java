/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import static OCMS.Entity.Users_.country;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.servlet.http.Part;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findAllSession", query = "select s from Session s")
    ,
    @NamedQuery(name = "countAllSession", query = "select COUNT(s) from Session s")
    ,
    @NamedQuery(name = "findUpcommingSession", query = "select s from Session s where s.sessionDateTime>:currentDateTime ORDER BY s.sessionDateTime DESC"),
    @NamedQuery(name = "findUpcommingTwoSession", query = "select s from Session s where s.sessionDateTime>:currentDateTime ORDER BY s.sessionDateTime DESC")
    ,
    @NamedQuery(name = "findSessionByName", query = "select s from Session s where s.sessionName=:sSessionName")
    ,
    @NamedQuery(name = "findSessionByTimeZone", query = "select s from Session s where s.timeZone=:sTimeZone")
    ,
    @NamedQuery(name = "findSessionById", query = "select s from Session s where s.sessionId=:sSessionId")

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
    private String imageUrl;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    @OneToMany(mappedBy = "sessionId")
    private List<SessionPaper> sessionPapers;
    @OneToMany(mappedBy = "sessionId")
    private List<SessionParticipant> sessionParticipants;
    @Transient
    private String sessionDateTimeStr;
    @Transient
    private Part image;

    public Session() {
    }

    public Session(Long sessionId, String sessionName, String timeZone, Date sessionDateTime, String imageUrl, String description, Date createdDate, Date updatedDate, List<SessionPaper> sessionPapers, List<SessionParticipant> sessionParticipants, String sessionDateTimeStr, Part image) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.timeZone = timeZone;
        this.sessionDateTime = sessionDateTime;
        this.imageUrl = imageUrl;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.sessionPapers = sessionPapers;
        this.sessionParticipants = sessionParticipants;
        this.sessionDateTimeStr = sessionDateTimeStr;
        this.image = image;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSessionDateTimeStr() {
        return sessionDateTimeStr;
    }

    public void setSessionDateTimeStr(String sessionDateTimeStr) {
        this.sessionDateTimeStr = sessionDateTimeStr;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Session{" + "sessionId=" + sessionId + ", sessionName=" + sessionName + ", timeZone=" + timeZone + ", sessionDateTime=" + sessionDateTime + ", imageUrl=" + imageUrl + ", description=" + description + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", sessionPapers=" + sessionPapers + ", sessionParticipants=" + sessionParticipants + ", sessionDateTimeStr=" + sessionDateTimeStr + ", image=" + image + '}';
    }

}
