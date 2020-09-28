/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.SessionParticipant;
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
    
     //find sessionPaper by session Id
    public List<SessionParticipant> findSessionParticipantBySessionId(Long sessionId) {
        List<SessionParticipant> sessionParticipantList= em.createNamedQuery("findSessionParticipantBySessionId").setParameter("sessionId", sessionId).getResultList();
        return sessionParticipantList;
    }
    //create a paper
    public SessionParticipant createSessionParticipant(SessionParticipant sessionParticipant) {
        em.persist(sessionParticipant);
        System.out.println(ctx.getCallerPrincipal().getName());
        return sessionParticipant;
    }
}
