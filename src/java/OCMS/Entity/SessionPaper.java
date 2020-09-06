/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findAllSessionPaper",query="select s from SessionPaper s"),
    @NamedQuery(name="findAllPaperIdBySessionId", query="select s from SessionPaper s where s.sessionId=:sSessionId")
    
})
public class SessionPaper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Session sessionId;
    private Paper paperId;

    public SessionPaper() {
    }

    public SessionPaper(Session sessionId, Paper paperId) {
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

    @Override
    public String toString() {
        return "SessionPaper{" + "id=" + id + ", sessionId=" + sessionId + ", paperId=" + paperId + '}';
    }
    
    
    
}
