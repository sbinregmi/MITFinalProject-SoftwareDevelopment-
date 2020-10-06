/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.TagsEJB;
import OCMS.EJB.UserEJB;
import OCMS.EJB.UserTagsEJB;
import OCMS.Entity.Tags;
import OCMS.Entity.UserTags;
import OCMS.Entity.Users;
import static OCMS.Entity.Users_.userTags;
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
    @EJB
    private UserTagsEJB userTagsEJB;
    @EJB
    private TagsEJB tagsEJB;
    private Users user = new Users();
    private List<Users> userList = new ArrayList<Users>();
    private boolean isJustCreated = false;
    private boolean isProfileUpdated = false;
    private boolean isUserLoggedIn = false;
    private boolean isUsernameOrEmailExists = false; //Used for password reset service

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

    public boolean getIsUsernameOrEmailExists() {
        return this.isUsernameOrEmailExists;
    }

    public void setIsUsernameOrEmailExists(boolean isUsernameOrEmailExists) {
        this.isUsernameOrEmailExists = isUsernameOrEmailExists;
    }

    //Create a new user
    public String createNewUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Users isUserExist = userEJB.findUserByEmailOrUsername(user.getUserName());
            if (isUserExist == null) {
                user.setIsActive(true);
                if (user.getRole() == null) {
                    user.setRole(Role.Organiser.toString());
                } else if (user.getRole().equals(Role.Participant)) {
                    user.setIsApproved(true);
                } else {
                    user.setIsApproved(false);
                }
                Date dateNow = new Date();
                user.setCreatedDate(dateNow);
                user = userEJB.createUser(user);
                if (user.getId() != null) {
                    UserTags userTag = new UserTags();
                    if (user.getTagList() != null) {
                        for (Long tag : user.getTagList()) {
                            userTag = new UserTags();
                            userTag.setUserId(user);
                            userTag.setUserTagId(tagsEJB.findTagById(tag));
                            userTag = userTagsEJB.createUserTag(userTag);
                        }
                        if (userTag != null) {
                            user = new Users();
                            context.addMessage("sucess", new FacesMessage("User registration is succeessful."));
                        } else {
                            for (UserTags uTag : userTagsEJB.findTagsByUserId(user.getId())) {
                                userTagsEJB.removeUserTags(uTag);
                            }
                            userEJB.deleteUser(user);
                        }
                    }

                } else {
                    context.addMessage("error", new FacesMessage("User registratiom is failed."));
                }
            } else {
                context.addMessage("error", new FacesMessage("Username or email already exist."));
            }
            
            return "register";
        } catch (Exception e) {
            context.addMessage("error", new FacesMessage("Internal server error. Please try later." + e.getLocalizedMessage()));
            return "register";
        }
    }

    public String resetPassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            String newPassword = user.getPassword();
            String userNameOrEmail = user.getUserName();
            user = userEJB.findUserByEmailOrUsername(userNameOrEmail);
            if (user != null) {
                if (newPassword == null || newPassword.isEmpty()) {
                    user = new Users();
                    user.setUserName(userNameOrEmail);
                    setIsUsernameOrEmailExists(true);
                    context.addMessage("success", new FacesMessage("Username/Email found. Reset your password."));
                    return "resetpassword";
                } else {
                    user.setPassword(newPassword);
                    user = userEJB.updateUser(user);
                    if (user != null) {
                        context.addMessage("success", new FacesMessage("Password reset is successful."));
                    } else {
                        context.addMessage("error", new FacesMessage("Password reset is failed."));
                    }
                }
            } else {
                context.addMessage("error", new FacesMessage("Username or email does not exist."));
            }
            return "resetpassword";
        } catch (Exception e) {
            context.addMessage("success", new FacesMessage("Internal server error. Please try later."));
            return "resetpassword";
        }
    }

    //Update profile
    @PermitAll
    public boolean updateProfile(Users user, UserEJB userEJB, UserTagsEJB userTagsEJB, TagsEJB tagsEJB) {
        Date dateNow = new Date();
        user.setUpdatedDate(dateNow);
        boolean tagAlreadExist = false;
        if (user.getTagList().size() == user.getUserTags().size()) {
            for (Long tagId : user.getTagList()) {
                tagAlreadExist = false;
                for (UserTags userTag : user.getUserTags()) {
                    if (tagId == userTag.getUserTagId().getId()) {
                        tagAlreadExist = true;
                        break;
                    }
                }
                if (!tagAlreadExist) {
                    break;
                }
            }
        }

        if (!tagAlreadExist) {
            for (UserTags uTag : userTagsEJB.findTagsByUserId(user.getId())) {
                userTagsEJB.removeUserTags(uTag);
            }
            List<UserTags> newUserTagList = new ArrayList<UserTags>();
            for (Long tagId : user.getTagList()) {
                UserTags userTag = new UserTags();
                userTag.setUserId(user);
                userTag.setUserTagId(tagsEJB.findTagById(tagId));
                userTag = userTagsEJB.createUserTag(userTag);
                if (userTag != null) {
                    newUserTagList.add(userTag);
                }
            }
            user.setUserTags(newUserTagList);
        }
        user = userEJB.updateUser(user);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    @RolesAllowed("Administrator")
    public String approveUser() {
        user.setIsActive(true);
        user.setIsApproved(true);
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
        List<String> timeZoneList = new ArrayList<String>();
        for (String zoneId : zoneIds) {
            TimeZone timeZone = TimeZone.getTimeZone(zoneId);
            long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
            if (hours > 0) {
                timeZoneList.add(String.format("GMT+%d", hours));
            } else {
                timeZoneList.add(String.format("GMT%d", hours));
            }
        }
        timeZoneList = timeZoneList.stream().distinct().collect(Collectors.toList());
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
