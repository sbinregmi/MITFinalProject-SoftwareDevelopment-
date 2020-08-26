/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.RegistrationEJB;
import OCMS.Entity.Registration;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author SabinRegmi
 */

@DeclareRoles({"Administrator", "ConferenceManager", "Author", "Participant"})
public class AccountController {
    @EJB
    private RegistrationEJB registrationEJB;
    
    private Registration user = new Registration();
    private List<Registration> userList = new ArrayList<Registration>();
    private boolean isJustCreated = false;
    private boolean isProfileUpdated=false;
    
     // Public Methods           
    public String createNewUser() {
        user.setIsActive(true);
        user.setIsApproved(false);
        user.setUserName(user.getEmail());
        Date dateNow=new Date();
        user.setCreatedDate((java.sql.Date)dateNow);
        user = registrationEJB.createUser(user);
      
        return "registration.xhtml";
    }
    
    //Update profile
    @PermitAll
    public String updateProfile(){
        isProfileUpdated=false;
        Date dateNow=new Date();
        user.setUpdatedDate((java.sql.Date)dateNow);
        user = registrationEJB.updateUser(user);
        if(user!=null){
            isProfileUpdated=true;
        }
        return "registration.xhtml";
    }
    
    @RolesAllowed("Administrator")
    public String approveUser() {
        user.setIsActive(true);
        user.setIsApproved(true);
        user.setUserName(user.getEmail());
        Date dateNow=new Date();
        user.setCreatedDate((java.sql.Date)dateNow);
        user = registrationEJB.approveUser(user);
        return "registration.xhtml";
    }
    
    //Remove a user
    @RolesAllowed("Administrator")
    public String deleteUser() {
        registrationEJB.deleteUser(user);
        return "registration.xhtml";
    }
    
    //Find user by user id
    @RolesAllowed({"Administrator","ConferenceManager"})
    public String findUserById() {
        user=registrationEJB.findUserById(user.getId());
        return "registration.xhtml";
    }
    
    //List all user
    @RolesAllowed({"Administrator","ConferenceManager"})
    public String findAllUser() {
        userList=registrationEJB.findAllUser();
        return "registration.xhtml";
    }
    
    //List user by role
    @RolesAllowed({"Administrator","ConferenceManager"})
    public String findUserByRole() {
        userList=registrationEJB.findUserByRole(user.getRole());
        return "registration.xhtml";
    }
    
    
    public String userogin() {
        user = registrationEJB.loginUser(user.getUserName(), user.getPassword());
        FacesContext context = FacesContext.getCurrentInstance();
        if (user.getId() != null) {
            context.getExternalContext().getSessionMap().put("user", user);
            return "home?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage("Username or password wrong. Try again"));
            user.setUserName(null);
            user.setPassword(null);
            return null;
        }
    }
    
    @PermitAll
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
}
