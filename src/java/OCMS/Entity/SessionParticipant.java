/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findAllSessionParticipant",query="select s from SessionParticipant s"),
    @NamedQuery(name="findAllParticipantIdBySessionId", query="select s from SessionParticipant s where s.sessionId=:sSessionId")
    
})
public class SessionParticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @ManyToOne
    private Session sessionId;
    @ManyToOne
    private Users participantId;
    private boolean isApproved;

    public SessionParticipant() {
    }

    public SessionParticipant(Session sessionId, Users participantId, Boolean isApproved) {
        this.sessionId = sessionId;
        this.participantId = participantId;
        this.isApproved=isApproved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSessionId() {
        return sessionId;
    }

    public void setSessionId(Session sessionId) {
        this.sessionId = sessionId;
    }

    public Users getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Users participantId) {
        this.participantId = participantId;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    @Override
    public String toString() {
        return "SessionParticipant{" + "id=" + id + ", sessionId=" + sessionId + ", participantId=" + participantId + '}';
    }
    
}
