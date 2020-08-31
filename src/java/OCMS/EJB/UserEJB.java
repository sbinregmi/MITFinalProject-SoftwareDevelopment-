/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Registration;
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
public class RegistrationEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;

    //find all User
    public List<Registration> findAllUser() {
        Query query = em.createNamedQuery("Registration.findAllUser");
        return query.getResultList();
    }

    //find users by role
    public List<Registration> findUserByRole(String role) {
        return em.createNamedQuery("Registration.findUserByRole").setParameter("role", role).getResultList();
    }

    //find users by Id
    public Registration findUserById(Long id) {
        return em.find(Registration.class, id);
    }

    //create a User
    public Registration createUser(Registration registration) {
        em.persist(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
        return registration;
    }
    
    //Approve a User
    public Registration approveUser(Registration registration) {
        registration.setIsApproved(true);
        em.merge(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
        return registration;
    }
    
    //update a User
    public Registration updateUser(Registration registration) {
        em.merge(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
        return registration;
    }
    
     //delete a User
    public void deleteUser(Registration registration) {
        registration=em.find(Registration.class, registration.getId());
        em.remove(registration);
        System.out.println(ctx.getCallerPrincipal().getName());
    }
    
    //Checking login credential
    public Registration loginUser(String userName,String password) {
        return (Registration) em.createNamedQuery("Registration.loginUser")
                .setParameter("userName", userName)
                .setParameter("password", password).getSingleResult();
    }
}
