/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import OCMS.ModelData.Enum;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findAllSessionPaper",query="select s from SessionPaper s"),
    @NamedQuery(name="countAllSessionPaper",query="select COUNT(s) from SessionPaper s"),
    @NamedQuery(name="findAllPaperIdBySessionId", query="select s from SessionPaper s where s.sessionId=:sessionId"),
    @NamedQuery(name="findSessionPaperByPaperId", query="select s from SessionPaper s where s.paperId=:paperId"),
    @NamedQuery(name="findSessionPaperBySessionId", query="select s from SessionPaper s where s.sessionId=:sessionId")
    
})
public class SessionPaper implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    Enum.Status status;
    @ManyToOne
    private Session sessionId;
    @ManyToOne
    private Paper paperId;

    public SessionPaper() {
    }

    public SessionPaper(Long id, Enum.Status status, Session sessionId, Paper paperId) {
        this.id = id;
        this.status = status;
        this.sessionId = sessionId;
        this.paperId = paperId;
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

    public Paper getPaperId() {
        return paperId;
    }

    public void setPaperId(Paper paperId) {
        this.paperId = paperId;
    }

    public Enum.Status getStatus() {
        return status;
    }

    public void setStatus(Enum.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SessionPaper{" + "id=" + id + ", sessionId=" + sessionId + ", paperId=" + paperId + '}';
    }
    
    
    
}
