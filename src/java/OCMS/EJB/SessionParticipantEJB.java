/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Session;
import OCMS.Entity.SessionParticipant;
import OCMS.Entity.Users;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SabinRegmi
 */
@Stateless
public class SessionParticipantEJB {

   @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    //find session by Id
    public SessionParticipant findSessionParticipantById(Long id) {
        return em.find(SessionParticipant.class, id);
    }
      //count sessionPaarticipant
    public Long countAllSessionParticipant() {
        Long sessionParticipantNumber= (Long)em.createNamedQuery("countAllSessionParticipant").getSingleResult();
        return sessionParticipantNumber;
    }
    
     //find sessionPaarticipant by session Id
    public List<SessionParticipant> findSessionParticipantBySessionId(Session session) {
        List<SessionParticipant> sessionParticipantList= em.createNamedQuery("findSessionParticipantBySessionId").setParameter("sessionId", session).getResultList();
        return sessionParticipantList;
    }
    
    //find session participant by participant
    public List<SessionParticipant> findSessionParticipantByParticipantId(Users participant) {
        List<SessionParticipant> sessionParticipantList= em.createNamedQuery("findSessionParticipantByParticipantId").setParameter("participantId", participant).getResultList();
        return sessionParticipantList;
    }
    
    //update a SessionParticipant
    public SessionParticipant updateSessionParticipant(SessionParticipant sessionParticipant) {
        SessionParticipant s = em.merge(sessionParticipant);
        System.out.println(ctx.getCallerPrincipal().getName());
        return s;
    }
    //create a session participant
    public SessionParticipant createSessionParticipant(SessionParticipant sessionParticipant) {
        em.persist(sessionParticipant);
        System.out.println(ctx.getCallerPrincipal().getName());
        return sessionParticipant;
    }
    
    //delete a session Participant
    public void deleteSessionParticipant(SessionParticipant sessionParticipant) {
        sessionParticipant=em.find(SessionParticipant.class, sessionParticipant.getId());
        em.remove(sessionParticipant);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
}
