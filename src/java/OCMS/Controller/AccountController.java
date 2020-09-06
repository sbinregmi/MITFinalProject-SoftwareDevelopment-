/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.UserEJB;
import OCMS.Entity.Users;
import OCMS.ModelData.Enum.Role;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author SabinRegmi
 */
@ManagedBean(name = "accountController")
@RequestScoped
@DeclareRoles({"Administrator", "ConferenceManager", "Author", "Participant"})
public class AccountController {

    @EJB
    private UserEJB userEJB;

    private Users user = new Users();
    private List<Users> userList = new ArrayList<Users>();
    private boolean isJustCreated = false;
    private boolean isProfileUpdated = false;
    private boolean isUserLoggedIn = false;

    // Public Methods    
    //Getters & Setters   
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean isUserLoggedInGet() {
        return this.isUserLoggedIn;
    }

    public void isUserLoggedInSet(boolean isUserLoggedIn) {
        this.isUserLoggedIn = isUserLoggedIn;
    }

    public String createNewUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        user.setIsActive(true);
        if (user.getRole().equals(Role.Participant)) {
            user.setIsApproved(true);
        } else {
            user.setIsApproved(false);
        }
        
        Date dateNow = new Date();
        user.setCreatedDate(dateNow);
        user = userEJB.createUser(user);
        if(user!=null){
            context.addMessage(null, new FacesMessage("User registration is succeessful."));
        }

        return "register.xhtml";
    }

    //Update profile
    @PermitAll
    public String updateProfile() {
        isProfileUpdated = false;
        Date dateNow = new Date();
        user.setUpdatedDate(dateNow);
        user = userEJB.updateUser(user);
        if (user != null) {
            isProfileUpdated = true;
        }
        return "registration.xhtml";
    }

    @RolesAllowed("Administrator")
    public String approveUser() {
        user.setIsActive(true);
        user.setIsApproved(true);
        user.setUserName(user.getEmail());
        Date dateNow = new Date();
        user.setCreatedDate(dateNow);
        user = userEJB.approveUser(user);
        return "registration.xhtml";
    }

    //Remove a user
    @RolesAllowed("Administrator")
    public String deleteUser() {
        userEJB.deleteUser(user);
        return "registration.xhtml";
    }

    //Find user by user id
    @RolesAllowed({"Administrator", "ConferenceManager"})
    public String findUserById() {
        user = userEJB.findUserById(user.getId());
        return "registration.xhtml";
    }

    //List all user
    @RolesAllowed({"Administrator", "ConferenceManager"})
    public String findAllUser() {
        userList = userEJB.findAllUser();
        return "registration.xhtml";
    }

    //List user by role
    @RolesAllowed({"Administrator", "ConferenceManager"})
    public String findUserByRole() {
        userList = userEJB.findUserByRole(user.getRole());
        return "registration.xhtml";
    }

    public String userLogin() {
        user = userEJB.loginUser(user.getUserName(), user.getPassword());
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (user != null) {
                String returnPageUrl = "";
                if (user.getRole().equals("Admin")) {
                    returnPageUrl = "/admin/dashboard?faces-redirect=true";
                } else if (user.getRole().equals("Author") && user.isIsApproved()) {
                    returnPageUrl = "/author/dashboard?faces-redirect=true";
                } else if (user.getRole().equals("Organizator") && user.isIsApproved()) {
                    returnPageUrl = "/organizer/dashboard?faces-redirect=true";
                } else if (user.getRole().equals("Participant")) {
                    returnPageUrl = "/participant/dashboard?faces-redirect=true";
                } else {
                    context.addMessage(null, new FacesMessage("User is not approved. Try again"));
                    user.setUserName(null);
                    user.setPassword(null);
                    return null;
                }
                return returnPageUrl;

            } else {
                context.addMessage(null, new FacesMessage("Username or password wrong. Try again"));
                return null;
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("Internal server error. Please try later"));
            return null;
        }
    }

    @PermitAll
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    //Return list of country name
    public ArrayList<String> getListOfCountries() {
        String[] countryCodes = Locale.getISOCountries();
        ArrayList<String> listOfCountries = new ArrayList<String>();;
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            String name = locale.getDisplayCountry();
            listOfCountries.add(name);
        }
        Collections.sort(listOfCountries);
        return listOfCountries;
    }

    //Return list of time zone
    public List<String> getListOfTimeZones() {
        String[] zoneIds = TimeZone.getAvailableIDs();
        List<String>  timeZoneList = new ArrayList<String>();
        for (String zoneId : zoneIds) {
            TimeZone timeZone = TimeZone.getTimeZone(zoneId);
            long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
            if (hours > 0) {
                timeZoneList.add(String.format("GMT+%d", hours));
            } else {
                timeZoneList.add(String.format("GMT%d", hours));
            }
        }
        timeZoneList=timeZoneList.stream().distinct().collect(Collectors.toList());
        Collections.sort(timeZoneList);
        return timeZoneList;
    }

//    @PostConstruct
//    private void init() {
//        try {
//            ServletRequest request = null;
//            HttpServletRequest servletRequest = (HttpServletRequest) request;
//
//            Users session = (Users) servletRequest.getSession().getAttribute("Users");
//            this.user = session;
//            if(user==null){
//                isUserLoggedInSet(true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
