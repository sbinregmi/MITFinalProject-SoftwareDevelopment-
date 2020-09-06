/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.ContactEJB;
import OCMS.EJB.UserEJB;
import OCMS.Entity.Contact;
import OCMS.Entity.Users;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author SabinRegmi
 */
@ManagedBean(name = "contactController")
@RequestScoped
public class ContactController {

    @EJB
    private ContactEJB contactEJB;
    private Contact contact = new Contact();
    
    //getter setter for contact
    public void setContact(Contact contact){
        this.contact=contact;
    }
    public Contact getContact(){
        return this.contact;
    }
    public String createContact() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Date dateNow = new Date();
            contact.setCreatedDate(dateNow);
            contact = contactEJB.createContact(contact);

            if (contact != null) {
                context.addMessage(null, new FacesMessage("Thank You. Your query successfully submitted."));
            } else {
                context.addMessage(null, new FacesMessage("Internal server error. Please try later."));
            }
            return "contact.xhtml";
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("Internal server error. Please try later."));
            return "contact.xhtml";
        }

    }
}
