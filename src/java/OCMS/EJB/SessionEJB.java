/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Session;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.TimeZone;

/**
 *
 * @author SabinRegmi
 */
@Stateless
public class SessionEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;

    //find all Session
    public List<Session> findAllSession() {
        Query query = em.createNamedQuery("findAllSession");
        return query.getResultList();
    }

    //find upcomming session/conference
    public Long countAllSession() {
        return (Long) em.createNamedQuery("countAllSession").getSingleResult();
    }

    //find upcomming session/conference
    public List<Session> findUpcommingSession() {
        Date currentDate = new Date();
        return em.createNamedQuery("findUpcommingSession").setParameter("currentDateTime", currentDate).getResultList();
    }
    
    //find upcomming two session/conference
    public List<Session> findUpcommingTwoSession() {
        Date currentDate = new Date();
        return em.createNamedQuery("findUpcommingTwoSession").setParameter("currentDateTime", currentDate).setMaxResults(2).getResultList();
    }

    //find session by session name
    public List<Session> findSessionByName(String name) {
        return em.createNamedQuery("Session.findSessionByName").setParameter("sessionName", name).getResultList();
    }

    //find session by Id
    public Session findSessionById(Long id) {
        return em.find(Session.class, id);
    }

    //find session by time zone
    public List<Session> findSessionByTimeZone(TimeZone timeZone) {
        return em.createNamedQuery("Session.findSessionByTimeZone").setParameter("timeZone", timeZone).getResultList();
    }
    //update a Session
    public Session updateSession(Session session) {
        Session s = em.merge(session);
        System.out.println(ctx.getCallerPrincipal().getName());
        return s;
    }

    //create a session
    public Session createSession(Session session) {
        em.persist(session);
        System.out.println(ctx.getCallerPrincipal().getName());
        return session;
    }
    
    //delete a session
    public void deleteSession(Session session) {
        session=em.find(Session.class, session.getSessionId());
        em.remove(session);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
}
