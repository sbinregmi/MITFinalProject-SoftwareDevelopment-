/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.TagsEJB;
import OCMS.Entity.Tags;
import OCMS.Entity.Users;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
    private Tags tag = new Tags();
    private Users user = new Users();

    //setter amd getter for user
    public Tags getTag() {
        return this.tag;
    }

    public void setTag(Tags user) {
        this.tag = tag;
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
    public String addTag() {
        boolean imageUploaded = false;
        Part image = tag.getImage();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String imageDir = File.separator + "images" + File.separator + "tags" + File.separator;
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
        String realPath=servletContext.getRealPath("")+imageUrl;
        File outputFile = new File(realPath);
        int num = 1;
        while (outputFile.exists()) {
            imageUrl = imageDir + tag.getName() + num + "." + extensionOfImage;
            outputFile = new File(servletContext.getRealPath("")+imageUrl);
            num++;
        }
        tag.setUrl(imageUrl);
        tag = tagsEJB.createTag(tag);
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

        String baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        return baseUrl + "/admin/addTag.xhtml";
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
