/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.UserEJB;
import javax.ejb.EJB;
import OCMS.Entity.Users;
import com.sun.faces.action.RequestMapping;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author SabinRegmi
 */
@ManagedBean(name = "authorController")
@RequestScoped
@RolesAllowed({"Administrator", "ConferenceManager", "Author", "Participant"})
public class AuthorController {

    @EJB
    private UserEJB userEJB;
    private Users user = new Users();

    //setter amd getter for user
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @RequestMapping("/dashboard")
    public String authorDashboard() {
        
        return "/author/dashboard.xhtml";
    }
     @RequestMapping(value="/profile.xhtml")
    public String authorProfile() {
        String baseUrl=FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        return baseUrl+"/author/profile.xhtml";
    }

    @PostConstruct
    private void init() {
        try {
            ServletRequest request = null;
            HttpServletRequest servletRequest = (HttpServletRequest) request;

            Users session = (Users) servletRequest.getSession().getAttribute("Users");
            this.user = session;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
