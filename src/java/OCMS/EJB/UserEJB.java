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
        Query query = em.createNamedQuery("Users.findAllUser");
        return query.getResultList();
    }

    //find users by role
    public List<Users> findUserByRole(String role) {
        return em.createNamedQuery("Users.findUserByRole").setParameter("role", role).getResultList();
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
    public Users approveUser(Users registration) {
        registration.setIsApproved(true);
        em.merge(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
        return registration;
    }
    
    //update a Users
    public Users updateUser(Users registration) {
        em.merge(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
        return registration;
    }
    
     //delete a Users
    public void deleteUser(Users registration) {
        registration=em.find(Users.class, registration.getId());
        em.remove(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
    
    //Checking login credential
    public Users loginUser(String userName,String password) {
        return (Users) em.createNamedQuery("Users.loginUser")
                .setParameter("userName", userName)
                .setParameter("password", password).getSingleResult();
    }
}
