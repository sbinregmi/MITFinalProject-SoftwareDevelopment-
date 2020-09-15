/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.PaperTags;
import OCMS.Entity.UserTags;
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
public class UserTagsEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    //create a UserTags

    public UserTags createUserTag(UserTags userTags) {
        em.persist(userTags);
        System.out.println(ctx.getCallerPrincipal().getName());
        return userTags;
    }

    //create a UserTag list
    public List<UserTags> createUserTagList(List<UserTags> userTags, Users user) {
        for (UserTags userTag : userTags) {
            userTag.setUserId(user);
            em.persist(userTag);
        }
        List<UserTags> userTagsList = em.createNamedQuery("findTagsByUserId").setParameter("userId", user.getId()).getResultList();
        if (userTagsList == null) {
            return null;
        } else {
            return userTagsList;
        }
    }

    //find user tag by user id
    public List<UserTags> findTagsByUserId(Long userId) {
        List<UserTags> userTagsList = em.createNamedQuery("findTagsByUserId").setParameter("userId", userId).getResultList();
        if (userTagsList == null) {
            return null;
        } else {
            return userTagsList;
        }
    }

    //update a UserTags
    public UserTags updateUserTag(UserTags userTags) {
        UserTags u = em.merge(userTags);
        System.out.println(ctx.getCallerPrincipal().getName());
        return u;
    }

    //delete a UserTags
    public void removeUserTags(UserTags userTag) {
        if (!em.contains(userTag)) {
            userTag = em.merge(userTag);
        }
        em.remove(userTag);
        System.out.println(ctx.getCallerPrincipal().getName());
    }

}
