/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.TagsEJB;
import OCMS.Entity.Tags;
import OCMS.Entity.Users;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author SabinRegmi
 */
@ManagedBean(name = "homeController")
@RequestScoped
@PermitAll
public class HomeController {

    @EJB
    private TagsEJB tagsEJB;
    private Tags tag = new Tags();

    //setter amd getter for user
    public Tags getTag() {
        return this.tag;
    }

    public List<Tags> getAllTags() {
        return tagsEJB.findAllTags();
    }
    
}