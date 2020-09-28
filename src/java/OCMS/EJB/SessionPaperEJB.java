/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Session;
import OCMS.Entity.SessionPaper;
import java.util.List;
import java.util.TimeZone;
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
public class SessionPaperEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    //find session by Id
    public SessionPaper findSessionPaperById(Long id) {
        return em.find(SessionPaper.class, id);
    }
    
     //find sessionPaper by session Id
    public List<SessionPaper> findSessionPaperBySessionId(Long sessionId) {
        List<SessionPaper> sessionPaperList= em.createNamedQuery("findSessionPaperBySessionId").setParameter("sessionId", sessionId).getResultList();
        return sessionPaperList;
    }
    //create a paper
    public SessionPaper createSessionPaper(SessionPaper sessionPaper) {
        em.persist(sessionPaper);
        System.out.println(ctx.getCallerPrincipal().getName());
        return sessionPaper;
    }
}
