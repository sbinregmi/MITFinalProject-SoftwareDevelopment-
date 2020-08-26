/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Session;
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
        Query query = em.createNamedQuery("Session.findAllSession");
        return query.getResultList();
    }

    //find session by session name
    public List<Session> findSessionByName(String name) {
        return em.createNamedQuery("Session.findSessionByName").setParameter("sessionName", name).getResultList();
    }

    //find session by Id
    public Session findSessionById(String id) {
        return em.find(Session.class, id);
    }
    
    //find session by time zone
    public List<Session> findSessionByTimeZone(TimeZone timeZone) {
        return em.createNamedQuery("Session.findSessionByTimeZone").setParameter("timeZone", timeZone).getResultList();
    }

    //create a paper
    public Session createSession(Session session) {
        em.persist(session);
        System.out.println(ctx.getCallerPrincipal().getName());
        return session;
    }
}
