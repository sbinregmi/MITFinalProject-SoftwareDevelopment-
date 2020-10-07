/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.PaperEJB;
import OCMS.EJB.PaperTagsEJB;
import OCMS.EJB.SessionEJB;
import OCMS.EJB.SessionParticipantEJB;
import OCMS.EJB.TagsEJB;
import OCMS.EJB.UserEJB;
import OCMS.EJB.UserTagsEJB;
import OCMS.Entity.Paper;
import OCMS.Entity.PaperTags;
import OCMS.Entity.Session;
import OCMS.Entity.SessionPaper;
import OCMS.Entity.SessionParticipant;
import OCMS.Entity.Tags;
import OCMS.Entity.UserTags;
import OCMS.Entity.Users;
import OCMS.ModelData.Enum.Role;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author SabinRegmi
 */
@ManagedBean(name = "adminController")
@RequestScoped
public class AdminController {

    @EJB
    private TagsEJB tagsEJB;
    @EJB
    private UserEJB usersEJB;
    @EJB
    private PaperEJB paperEJB;
    @EJB
    private SessionEJB sessionEJB;
    @EJB
    private UserTagsEJB userTagsEJB;
    @EJB
    private PaperTagsEJB paperTagsEJB;
    @EJB
    private SessionParticipantEJB sessionParticipantEJB;

    @Inject
    HttpServletRequest request;
    private Users user = new Users();
    private Tags tag = new Tags();
    private int noOfPaper;
    private int noOfParticipants;
    private int noOfConference;
    private int noOfAuthors;
    private Users organiser = new Users();

    public int getNoOfPaper() {
        return noOfPaper;
    }

    public Users getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Users organiser) {
        this.organiser = organiser;
    }

    public void setNoOfPaper(int noOfPaper) {
        this.noOfPaper = noOfPaper;
    }

    public int getNoOfParticipants() {
        return noOfParticipants;
    }

    public void setNoOfParticipants(int noOfParticipants) {
        this.noOfParticipants = noOfParticipants;
    }

    public int getNoOfConference() {
        return noOfConference;
    }

    public void setNoOfConference(int noOfConference) {
        this.noOfConference = noOfConference;
    }

    public int getNoOfAuthors() {
        return noOfAuthors;
    }

    public void setNoOfAuthors(int noOfAuthors) {
        this.noOfAuthors = noOfAuthors;
    }

    public int getNoOfOrganisers() {
        return noOfOrganisers;
    }

    public void setNoOfOrganisers(int noOfOrganisers) {
        this.noOfOrganisers = noOfOrganisers;
    }
    private int noOfOrganisers;

    //setter and getter for user
    public Tags getTag() {
        return this.tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

//    @RequestMapping(value = "/addTag")
//    @GET
//    public String addTagGet() {
//        String baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
//        return baseUrl + "/admin/addTag.xhtml";
//    }
//    
//    @RequestMapping(value = "/addTag")
//    @POST
    public void addTag() {
        boolean imageUploaded = false;
        Part image = tag.getImage();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String imageDir = File.separator + "resource" + File.separator + "tags" + File.separator;
        File dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdirs(); //create directory /images/tags if not exist
        }
        String extensionOfImage = "";
        int i = image.getSubmittedFileName().lastIndexOf('.');
        if (i > 0) {
            extensionOfImage = image.getSubmittedFileName().substring(i + 1);
        }
        String imageUrl = imageDir + tag.getName() + "." + extensionOfImage;
        String realPath = servletContext.getRealPath("") + imageUrl;
        File outputFile = new File(realPath);
        int num = 1;
        while (outputFile.exists()) {
            imageUrl = imageDir + tag.getName() + num + "." + extensionOfImage;
            outputFile = new File(servletContext.getRealPath("") + imageUrl);
            num++;
        }
        tag.setUrl(servletContext.getContextPath() + imageUrl);
        tag = tagsEJB.createTag(tag);
        context.addMessage("success", new FacesMessage("Tag is inserted successfully."));
        InputStream inputStream = null;
        OutputStream outputStream = null;
        if (tag.getImage().getSize() > 0) {
            try {
                inputStream = image.getInputStream();

                outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                imageUploaded = true;
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!imageUploaded) {
            tag.setUrl("");
            tagsEJB.updateTag(tag);
        }
        tag = new Tags();
        //return "addTag";
    }

    public List<Tags> getAllTags() {
        return tagsEJB.findAllTags();
    }

    public List<Users> getAllUsers() {
        return usersEJB.findAllUser();
    }

    public List<Users> getAllOrganisers() {
        return usersEJB.findUserByRole(Role.Organizer.name());
    }

    public List<Users> getAllParticipants() {
        return usersEJB.findUserByRole(Role.Participant.name());
    }

    public List<Users> getAllAuthors() {
        return usersEJB.findUserByRole(Role.Author.name());
    }

    public List<Paper> getAllPapers() {
        return paperEJB.findAllPaper();
    }

    public void updateProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        user.setUpdatedDate(new Date());
        usersEJB.updateUser(user);
    }

    public String deleteUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestedParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestedParamMap.containsKey("userIdToBeDeleted")) {
            }

        } catch (Exception exp) {

        }
        return "manageOrganiser";
    }

    //POST: DELETE A Tag BY Tag ID
    public String deleteTag() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("tagIdToBeDeleted")) {
                Long id = Long.parseLong(requestParamMap.get("tagIdToBeDeleted"));
                Tags tag = tagsEJB.findTagById(id);
                List<UserTags> userTagList = userTagsEJB.findUserTagsByTagId(id);
                for (UserTags userTag : userTagList) {
                    userTagsEJB.removeUserTags(userTag);
                }
                List<PaperTags> paperTagList = paperTagsEJB.findPaperTagsByTagId(id);
                for (PaperTags paperTag : paperTagList) {
                    paperTagsEJB.remove(paperTag.getId());
                }

                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                boolean result = false;
                String imageUrl = tag.getUrl();
                if (!imageUrl.isEmpty() && imageUrl != null) {
                    imageUrl = imageUrl.replace(servletContext.getContextPath(), "");
                    imageUrl = imageUrl.replace("\\", "/");
                    File previousFile = new File(servletContext.getRealPath(imageUrl));
                    result = Files.deleteIfExists(previousFile.toPath());
                }
                tagsEJB.remove(tag);
                context.addMessage("success", new FacesMessage("Tag is deleted successfully."));
                return "tag/tags";
            } else {
                context.addMessage("error", new FacesMessage("Internal server error."));
                return "tag/tags";
            }
        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "tag/tags";
        }

    }

    public String editTag() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
            if (paramMap.containsKey("id")) {
                Long id = Long.parseLong(paramMap.get("id"));
                Tags tag = tagsEJB.findTagById(id);
                setTag(tag);
                return "updatetag.xhtml";
            } else {
                context.addMessage("error", new FacesMessage("Internal Server Error"));
                return "tags.xhtml";
            }
        } catch (Exception exp) {
            context.addMessage("error", new FacesMessage("Internal Server Error"));
            return "tags.xhtml";
        }
    }

    //update Tags
    public String updateTag() {

        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        try {
            Tags oldTag = tagsEJB.findTagById(tag.getId());

            String oldImageUrl = oldTag.getUrl();
            if (!oldImageUrl.isEmpty() && oldImageUrl != null) {
                oldImageUrl = oldImageUrl.replace(servletContext.getContextPath(), "");
                oldImageUrl = oldImageUrl.replace("\\", "/");
                File previousFile = new File(servletContext.getRealPath(oldImageUrl));
                boolean result = Files.deleteIfExists(previousFile.toPath());
            }

            boolean imageUploaded = false;
            Part image = tag.getImage();
            if (image != null) {

                String imageDir = File.separator + "resource" + File.separator + "tags" + File.separator;
                File dir = new File(imageDir);
                if (!dir.exists()) {
                    dir.mkdirs(); //create directory /images/tags if not exist
                }
                String extensionOfImage = "";
                int i = image.getSubmittedFileName().lastIndexOf('.');
                if (i > 0) {
                    extensionOfImage = image.getSubmittedFileName().substring(i + 1);
                }
                String imageUrl = imageDir + tag.getName() + "." + extensionOfImage;
                String realPath = servletContext.getRealPath("") + imageUrl;
                File outputFile = new File(realPath);
                int num = 1;
                while (outputFile.exists()) {
                    imageUrl = imageDir + tag.getName() + num + "." + extensionOfImage;
                    outputFile = new File(servletContext.getRealPath("") + imageUrl);
                    num++;
                }
                oldTag.setUrl(servletContext.getContextPath() + imageUrl);
                InputStream inputStream = null;
                OutputStream outputStream = null;
                if (tag.getImage().getSize() > 0) {
                    try {
                        inputStream = image.getInputStream();

                        outputStream = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[1024];
                        int bytesRead = 0;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        imageUploaded = true;
                    } catch (IOException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            if (!imageUploaded) {
                oldTag.setUrl("");

            }
            oldTag.setDescription(tag.getDescription());
            oldTag.setName(tag.getName());
            tag = tagsEJB.updateTag(oldTag);
            context.addMessage("success", new FacesMessage("Tag is updated Successfully."));
            return "updatetag";
        } catch (Exception exp) {
            context.addMessage("error", new FacesMessage("Internal Server Error"));
            return "updatetag";
        }

    }

    public String deleteParticipant() {

        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestedParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestedParamMap.containsKey("userIdToBeDeleted")) {
                Long participantId = Long.parseLong(requestedParamMap.get("userIdToBeDeleted"));
                Users participant = usersEJB.findUserById(participantId);
                List<UserTags> userTagList = userTagsEJB.findTagsByUserId(participantId);
                for (UserTags userTag : userTagList) {
                    userTagsEJB.removeUserTags(userTag);
                }
                List<SessionParticipant> sessionParticipantsList = sessionParticipantEJB.findSessionParticipantByParticipantId(participant);
                for (SessionParticipant sessionParticipant : sessionParticipantsList) {
                    sessionParticipantEJB.deleteSessionParticipant(sessionParticipant);
                }
                usersEJB.deleteUser(participant);
                context.addMessage("success", new FacesMessage("Participant is deleted Successfully."));
            } else {
                context.addMessage("error", new FacesMessage("Internal Server Error!!"));
            }

        } catch (Exception exp) {
            context.addMessage("error", new FacesMessage("Internal Server Error!!"));
        }
        return "participant";
    }

    public String editOrganiser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
            if (paramMap.containsKey("organiserId")) {
                Long id = Long.parseLong(paramMap.get("organiserId"));
                Users olderOrganiser = usersEJB.findUserById(id);
                setOrganiser(olderOrganiser);
                return "organiser/updateOrganiser.xhtml";
            } else {
                context.addMessage("error", new FacesMessage("Internal Server Error"));
                return "manageOrganiser.xhtml";
            }
        } catch (Exception exp) {
            context.addMessage("error", new FacesMessage("Internal Server Error"));
            return "manageOrganiser.xhtml";
        }
    }

    public String updateOrganiser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Users olderOrganiser = usersEJB.findUserById(organiser.getId());
            olderOrganiser.setAddress(organiser.getAddress());
            olderOrganiser.setAffiliation(organiser.getAffiliation());
            olderOrganiser.setCountry(organiser.getCountry());
            olderOrganiser.setEmail(organiser.getEmail());
            olderOrganiser.setFirstName(organiser.getFirstName());
            olderOrganiser.setLastName(organiser.getLastName());
            olderOrganiser.setTimeZone(organiser.getTimeZone());
            olderOrganiser.setUserName(organiser.getUserName());
            olderOrganiser.setUpdatedDate(new Date());
            olderOrganiser.setPassword(organiser.getPassword());
            usersEJB.updateUser(olderOrganiser);
            context.addMessage("success", new FacesMessage("Organiser is updated successfully"));
        } catch (Exception exp) {
            context.addMessage("error", new FacesMessage("Internal Server Error"));
        }

        return "updateOrganiser";
    }

    @PostConstruct
    private void init() {
        try {
            HttpSession session = request.getSession();
            Users loggedInUser = (Users) session.getAttribute("user");
            setNoOfPaper(paperEJB.findAllPaper().size());
            setNoOfConference(sessionEJB.findAllSession().size());
            setNoOfAuthors(usersEJB.findUserByRole(Role.Author.name()).size());
            setNoOfOrganisers(usersEJB.findUserByRole(Role.Organizer.name()).size());
            setNoOfParticipants(usersEJB.findUserByRole(Role.Participant.name()).size());

            //this.user = usersEJB.findUserById(loggedInUser.getId());
            //setNumberOfPaperSubmmited(paperEJB.numberOfPaperSubmittedByAuthor(user.getId()));
            setUser(loggedInUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
