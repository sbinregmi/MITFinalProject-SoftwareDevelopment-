/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Users;
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
public class UserEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;

    //find all Users
    public List<Users> findAllUser() {
        Query query = em.createNamedQuery("findAllUser");
        return query.getResultList();
    }

    //find users by role
    public List<Users> findUserByRole(String role) {
        return em.createNamedQuery("findUserByRole").setParameter("role", role).getResultList();
    }

    //find users by Id
    public Users findUserById(Long id) {
        return em.find(Users.class, id);
    }

    //create a Users
    public Users createUser(Users registration) {
        em.persist(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
        return registration;
    }
    
    //Approve a Users
    public Users approveUser(Users user) {
        user.setIsApproved(true);
        em.merge(user);
        System.out.println(ctx.getCallerPrincipal().getName());
        return user;
    }
    
    //update a Users
    public Users updateUser(Users user) {
        Users u=em.merge(user);
        System.out.println(ctx.getCallerPrincipal().getName());
        return u;
    }
    
     //delete a Users
    public void deleteUser(Users user) {
        user=em.find(Users.class, user.getId());
        em.remove(user);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
    
    //Find user by username or email --For password reset purpose
    public Users findUserByEmailOrUsername(String userName) {
        List<Users> currentUser= em.createNamedQuery("findUserByEmailOrUsername")
                .setParameter("userName", userName)
                .setParameter("email", userName).getResultList();
        if (currentUser.isEmpty())
            return null;
        else
            return currentUser.get(0);
    }
    //Checking login credential
    public Users loginUser(String userName,String password) {
        List<Users> currentUser= em.createNamedQuery("loginUser")
                .setParameter("userName", userName)
                .setParameter("password", password)
                .setParameter("email", userName)
                .setParameter("password", password).getResultList();
        if (currentUser.isEmpty())
            return null;
        else
            return currentUser.get(0);
            
    }
}
