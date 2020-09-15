/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.PaperEJB;
import OCMS.EJB.PaperTagsEJB;
import OCMS.EJB.TagsEJB;
import OCMS.EJB.UserEJB;
import OCMS.EJB.UserTagsEJB;
import OCMS.Entity.Paper;
import OCMS.Entity.PaperTags;
import javax.ejb.EJB;
import OCMS.Entity.Users;
import com.sun.faces.action.RequestMapping;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
    @EJB
    private PaperEJB paperEJB;
    @EJB
    private PaperTagsEJB paperTagsEJB;
    @EJB
    private UserTagsEJB userTagsEJB;
    @EJB
    private TagsEJB tagsEJB;
    @Inject
    HttpServletRequest request;
    private Users user = new Users();
    private Paper paper = new Paper();
    private int numberOfPaperSubmmited;
    private SimpleDateFormat formaterForDate = new SimpleDateFormat("MMM dd, yyyy");

    public SimpleDateFormat getFormaterForDate() {
        formaterForDate = new SimpleDateFormat("MMM dd, yyyy");
        return formaterForDate;
    }

    public void setFormaterForDate(SimpleDateFormat formaterForDate) {
        this.formaterForDate = formaterForDate;
    }

    //setter amd getter for user
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Paper getPaper() {
        return this.paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public int getNumberOfPaperSubmmited() {
        return numberOfPaperSubmmited;
    }

    public void setNumberOfPaperSubmmited(int numberOfPaperSubmmited) {
        this.numberOfPaperSubmmited = numberOfPaperSubmmited;
    }

    @RequestMapping("/dashboard")
    public String authorDashboard() {

        return "/author/dashboard.xhtml";
    }

    @RequestMapping(value = "/profile.xhtml")
    public String authorProfile() {
        String baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        return baseUrl + "/author/profile.xhtml";
    }

    @RequestMapping(value = "/updateAuthor.xhtml")
    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        Users isUserExist = userEJB.findUserByEmailOrUsername(user.getUserName());
        if (isUserExist != null && isUserExist.getId() != user.getId()) {
            context.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is already in use.", null));
            return "profile";
        } else {
            isUserExist = userEJB.findUserByEmailOrUsername(user.getEmail());
            if (isUserExist != null && isUserExist.getId() != user.getId()) {
                context.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email is already in use.", null));
                return "profile";
            } else {
                AccountController accountController = new AccountController();
                boolean isUpdated = accountController.updateProfile(user, userEJB, userTagsEJB, tagsEJB);

                if (isUpdated) {
                    context.addMessage("success", new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile is successfully updated.", null));
                    return "profile";
                } else {
                    context.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal server occur. Please try later.", null));
                    return "profile";
                }
            }

        }
    }

    public String createPaper() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            boolean pdfUploaded = false;
            Part pdfFile = paper.getPdfFile();
            if (pdfFile != null) {
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String paperDir = File.separator + "resource" + File.separator + "paper" + File.separator;
                File dir = new File(paperDir);
                if (!dir.exists()) {
                    dir.mkdirs(); //create directory /resource/paper if not exist
                }
                String extentionOfPaper = "";
                int i = pdfFile.getSubmittedFileName().lastIndexOf('.');
                if (i > 0) {
                    extentionOfPaper = pdfFile.getSubmittedFileName().substring(i + 1);
                }
                if (extentionOfPaper.equalsIgnoreCase("pdf")) {
                    String pdfUrl = paperDir + paper.getPaperTitle() + "." + extentionOfPaper;
                    String realPath = servletContext.getRealPath("") + pdfUrl;
                    File outputFile = new File(realPath);
                    int num = 1;
                    while (outputFile.exists()) {
                        pdfUrl = paperDir + paper.getPaperTitle() + num + "." + extentionOfPaper;
                        outputFile = new File(servletContext.getRealPath("") + pdfUrl);
                        num++;
                    }

                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    if (paper.getPdfFile().getSize() > 0) {
                        try {
                            inputStream = pdfFile.getInputStream();

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
                            pdfUploaded = true;
                        } catch (IOException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pdfUploaded) {
                        paper.setPaperUrl(servletContext.getContextPath() + pdfUrl);
                        paper.setAuthorId(user);
                        Date dateNow = new Date();
                        paper.setCreatedDate(dateNow);
                        paper = paperEJB.createPaper(paper);
                        if (paper != null) {
                            PaperTags paperTag = new PaperTags();
                            for (Long tag : paper.getTagList()) {
                                paperTag = new PaperTags();
                                paperTag.setTagId(tagsEJB.findTagById(tag));
                                paperTag.setPaperId(paper);
                                paperTag = paperTagsEJB.createPaperTag(paperTag);
                            }
                            if (paperTag != null) {
                                paper = new Paper();
                                context.addMessage("success", new FacesMessage("Paper is uploaded successfully."));
                            } else {
                                paperTagsEJB.removeTagsByPaperId(paper.getPaperId());
                                paperEJB.deletePaper(paper);
                                context.addMessage("error", new FacesMessage("Paper upload failed."));
                            }
                        } else {
                            context.addMessage("error", new FacesMessage("Paper upload failed."));
                        }
                    } else {
                        context.addMessage("error", new FacesMessage("Paper upload failed."));
                    }

                } else {
                    context.addMessage("error", new FacesMessage("Upload paper in pdf format."));
                }

            } else {
                context.addMessage("error", new FacesMessage("Upload paper in pdf format."));
            }

            return "create";
        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "create";
        }
    }

    //Delete paper by paper id
    @RequestMapping(value="author/deletePaper")
    public String deletePaper(Long id) {
        paperTagsEJB.removeTagsByPaperId(id);
        paperEJB.deletePaperById(id);
        return "managePaper";
    }

    public List<Paper> getSubmittedPaper() {
        List<Paper> paperList = paperEJB.findPaperByAuthorId(user.getId());

        return paperList;
    }

    @PostConstruct
    private void init() {
        try {
            HttpSession session = request.getSession();
            Users loggedInUser = (Users) session.getAttribute("user");
            this.user = userEJB.findUserById(loggedInUser.getId());
            setNumberOfPaperSubmmited(paperEJB.numberOfPaperSubmittedByAuthor(user.getId()));
            setUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
