/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Tags;
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
public class TagsEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;

    //find all Tags
    public List<Tags> findAllTags() {
        Query query = em.createNamedQuery("findAllTags");
        return query.getResultList();
    }
    
    //update tag
    public Tags updateTag(Tags tag){
        em.merge(tag);
        System.out.println(ctx.getCallerPrincipal().getName());
        return tag;
    }
    
    //create a Tag
    public Tags createTag(Tags tag) {
        em.persist(tag);
        System.out.println(ctx.getCallerPrincipal().getName());
        return tag;
    }
}
