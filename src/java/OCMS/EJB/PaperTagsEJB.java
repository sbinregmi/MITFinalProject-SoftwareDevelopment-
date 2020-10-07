/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Paper;
import OCMS.Entity.PaperTags;
import java.util.ArrayList;
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
public class PaperTagsEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;

    //create a UserTags
    public PaperTags createPaperTag(PaperTags paperTags) {
        em.persist(paperTags);
        System.out.println(ctx.getCallerPrincipal().getName());
        return paperTags;
    }

    //create a UserTag list
    public boolean createPaperTagList(List<PaperTags> paperTags, Paper paper) {
        try {
            for (PaperTags paperTag : paperTags) {
                paperTag.setPaperId(paper);
                em.persist(paperTag);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
    

    //find paper tag by paper id
    public List<PaperTags> findTagsByPaperId(Long paperId) {
        List<PaperTags> paperTagsList = em.createNamedQuery("findTagsByPaperId").setParameter("paperId", paperId).getResultList();
        if (paperTagsList == null) {
            return null;
        } else {
            return paperTagsList;
        }
    }
    
    //find paperTags by Tag ID
    public List<PaperTags> findPaperTagsByTagId(Long tagId) {
        List<PaperTags> paperTagsList = em.createNamedQuery("findPaperTagsByTagId").setParameter("tagId", tagId).getResultList();
        if (paperTagsList == null) {
            return null;
        } else {
            return paperTagsList;
        }
    }
    
    

    //delete a UserTags
    public void removeTagsByPaperId(Long paperId) {
        em.createNamedQuery("removeTagsByPaperId").setParameter("paperId", paperId);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
    
    public void remove(Long paperId) {
        PaperTags paperTag=em.find(PaperTags.class, paperId);
         em.remove(paperTag);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
    //delete a UserTags
    public void removePaperTags(PaperTags paperTag) {
        if (!em.contains(paperTag)) {
            paperTag = em.merge(paperTag);
        }
        em.remove(paperTag);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
}
