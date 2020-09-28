/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Paper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
    
    @EJB
    private PaperTagsEJB paperTagsEJB;

    //find all Paper
    public List<Paper> findAllPaper() {
        Query query = em.createNamedQuery("findAllPaper");
        return query.getResultList();
    }

    //find paper by paper title
    public List<Paper> findPaperByTitle(String paperTitle) {
        return em.createNamedQuery("findPaperByTitle").setParameter("paperTitle", paperTitle).getResultList();
    }

    //find paper by Id
    public Paper findPaperById(Long id) {
        return em.find(Paper.class, id);
    }
    
//    //find paper by paper topic
//    public List<Paper> findPaperBytopic(String topic) {
//        return em.createNamedQuery("findPaperBytopic").setParameter("topic", topic).getResultList();
//    }

    //find paper by Author Id
    public List<Paper> findPaperByAuthorId(Long authorId) {
        List<Paper> paperList= em.createNamedQuery("findPaperByAuthorId").setParameter("authorId", authorId).getResultList();
        if(paperList==null){
            return null;
        }
        else{
            List<Paper> finalPaperList=new ArrayList<Paper>();
            for(Paper paper:paperList){
                paper.setPaperTags(paperTagsEJB.findTagsByPaperId(paper.getPaperId()));
                finalPaperList.add(paper);
            }
            return finalPaperList;
        }
    }
    
    //find number of paper submitted by a author
    public int numberOfPaperSubmittedByAuthor(Long authorId) {
        int numberOfPaper=0;
        List<Paper> paper=em.createNamedQuery("numberOfPaperSubmittedByAuthor").setParameter("authorId", authorId).getResultList();
        if(paper!=null)
            numberOfPaper=paper.size();
        return numberOfPaper;
    }

    //create a paper
    public Paper createPaper(Paper paper) {
        em.persist(paper);
        System.out.println(ctx.getCallerPrincipal().getName());
        return paper;
    }
    
    //update a Users
    public Paper updatePaper(Paper paper) {
        Paper p=em.merge(paper);
        System.out.println(ctx.getCallerPrincipal().getName());
        return p;
    }
     //delete a paper
    public void deletePaper(Paper paper) {
        paper=em.find(Paper.class, paper.getPaperId());
        em.remove(paper);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
     //delete a paper by ID
    public void deletePaperById(Long paperId) {
        Paper paper=em.find(Paper.class, paperId);
         em.remove(paper);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
}
