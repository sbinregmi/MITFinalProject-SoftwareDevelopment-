/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.EJB;

import OCMS.Entity.Contact;
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
public class ContactEJB {

    @PersistenceContext(unitName = "OCMSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;

    //find all Contact
    public List<Contact> findAllContact() {
        Query query = em.createNamedQuery("Contact.findAllContact");
        return query.getResultList();
    }

    //create a Contact
    public Contact createContact(Contact contact) {
        em.persist(contact);
        System.out.println(ctx.getCallerPrincipal().getName());
        return contact;
    }
}
