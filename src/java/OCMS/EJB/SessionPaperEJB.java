/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Paper;
import OCMS.Entity.Session;
import OCMS.Entity.SessionPaper;
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
public class SessionPaperEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    //find session by Id
    public SessionPaper findSessionPaperById(Long id) {
        return em.find(SessionPaper.class, id);
    }
    
    //count sessionPaper
    public Long countAllSessionPaper() {
        Long numberOfSessionPaper= (Long)em.createNamedQuery("countAllSessionPaper").getSingleResult();
        return numberOfSessionPaper;
    }
     //find sessionPaper by session Id
    public List<SessionPaper> findSessionPaperBySessionId(Session session) {
        List<SessionPaper> sessionPaperList= em.createNamedQuery("findSessionPaperBySessionId").setParameter("sessionId", session).getResultList();
        return sessionPaperList;
    }
    
    //find sessionPaper by paper Id
    public List<SessionPaper> findSessionPaperByPaperId(Paper paper) {
        List<SessionPaper> sessionPaperList= em.createNamedQuery("findSessionPaperByPaperId").setParameter("paperId", paper).getResultList();
        return sessionPaperList;
    }
    //update a SessionPaper
    public SessionPaper updateSessionPaper(SessionPaper sessionPaper) {
        SessionPaper s = em.merge(sessionPaper);
        System.out.println(ctx.getCallerPrincipal().getName());
        return s;
    }
    //create a sessionpaper
    public SessionPaper createSessionPaper(SessionPaper sessionPaper) {
        em.persist(sessionPaper);
        System.out.println(ctx.getCallerPrincipal().getName());
        return sessionPaper;
    }
    
    //delete a sessionPaper
    public void deleteSessionPaper(SessionPaper sessionPaper) {
        sessionPaper=em.find(SessionPaper.class, sessionPaper.getId());
        em.remove(sessionPaper);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
}
