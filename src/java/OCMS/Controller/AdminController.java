/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.PaperEJB;
import OCMS.EJB.SessionEJB;
import OCMS.EJB.TagsEJB;
import OCMS.EJB.UserEJB;
import OCMS.EJB.UserTagsEJB;
import OCMS.Entity.Paper;
import OCMS.Entity.Tags;
import OCMS.Entity.Users;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    
    
    @Inject
    HttpServletRequest request;
    private Users user = new Users();
    private Tags tag = new Tags();
    private int noOfPaper;
    private int noOfParticipants;
    private int noOfConference;
    private int noOfAuthors;

    public int getNoOfPaper() {
        return noOfPaper;
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
        tag.setUrl(imageUrl);
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
        return usersEJB.findUserByRole("Organiser");
    }

    public List<Users> getAllParticipants() {       
        return usersEJB.findUserByRole("Participant");
    }

    public List<Paper> getAllPapers() {
        return paperEJB.findAllPaper();
    }
    
    public void updateProfile(){
        FacesContext context = FacesContext.getCurrentInstance();
        user.setUpdatedDate(new Date());
        usersEJB.updateUser(user);
    }
    
    public String deleteUser(){
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            Map<String, String> requestedParamMap=context.getExternalContext().getRequestParameterMap();
            if(requestedParamMap.containsKey("userIdToBeDeleted")){
                
            }
            
        }catch (Exception exp){
            
        }
        
        
        return "manageOrganiser";
    }
    
    

//    public String editOrganiser(){
//        FacesContext context=FacesContext.getCurrentInstance();
//        try{
//            Map<String,String> paramMap=context.getExternalContext().getRequestParameterMap();
//            if(paramMap.containsKey("id")){
//                Long id=Long.parseLong(paramMap.get("id"));
//                Users user=usersEJB.findUserById(id);
//                System.out.println("Firstname :::"+user.getFirstName());
//                
//                
//                setUser(user);
//                return "organiser/updateOrganiser.xhtml";
//            }else{
//                context.addMessage("error", new FacesMessage("Internal Server Error"));
//                return "manageOrganiser.xhtml";
//            }
//        }catch(Exception exp){
//            context.addMessage("error", new FacesMessage("Internal Server Error"));
//            return "manageOrganiser.xhtml";
//        }
//    }
    
    
    @PostConstruct
    private void init() {
        try {
            HttpSession session = request.getSession();
            Users loggedInUser = (Users) session.getAttribute("user");
            setNoOfPaper(paperEJB.findAllPaper().size());
            setNoOfConference(sessionEJB.findAllSession().size());
            setNoOfAuthors(usersEJB.findUserByRole("Author").size());
            setNoOfOrganisers(usersEJB.findUserByRole("Organiser").size());
            setNoOfParticipants(usersEJB.findUserByRole("Participants").size());
            
            //this.user = usersEJB.findUserById(loggedInUser.getId());
            //setNumberOfPaperSubmmited(paperEJB.numberOfPaperSubmittedByAuthor(user.getId()));
            
            setUser(loggedInUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
