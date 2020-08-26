/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Paper;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author SabinRegmi
 */
@Stateless
public class PaperEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;

    //find all Paper
    public List<Paper> findAllPaper() {
        Query query = em.createNamedQuery("Paper.findAllPaper");
        return query.getResultList();
    }

    //find paper by paper title
    public List<Paper> findPaperByTitle(String paperTitle) {
        return em.createNamedQuery("Paper.findPaperByTitle").setParameter("paperTitle", paperTitle).getResultList();
    }

    //find paper by Id
    public Paper findPaperById(String id) {
        return em.find(Paper.class, id);
    }
    
    //find paper by paper topic
    public List<Paper> findPaperBytopic(String topic) {
        return em.createNamedQuery("Paper.findPaperBytopic").setParameter("topic", topic).getResultList();
    }

    //find paper by Author Id
    public List<Paper> findPaperByAuthorId(Long authorId) {
        return em.createNamedQuery("Paper.findPaperByAuthorId").setParameter("authorId", authorId).getResultList();
    }

    //create a paper
    public Paper createPaper(Paper paper) {
        em.persist(paper);
        System.out.println(ctx.getCallerPrincipal().getName());
        return paper;
    }
}
