/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.SessionEJB;
import OCMS.EJB.SessionPaperEJB;
import OCMS.EJB.TagsEJB;
import OCMS.EJB.UserEJB;
import OCMS.Entity.Session;
import OCMS.Entity.SessionPaper;
import OCMS.Entity.SessionParticipant;
import OCMS.Entity.Tags;
import OCMS.Entity.Users;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    @EJB
    private UserEJB userEJB;
    @EJB
    private SessionEJB sessionEJB;
    @EJB
    private SessionPaperEJB sessionPaperEJB;
    @Inject
    HttpServletRequest request;
    private Tags tag = new Tags();
    private Users user = new Users();
    private SimpleDateFormat formaterForDate = new SimpleDateFormat("dd MMMM");
    private SimpleDateFormat formaterForDateMMMMddYYYY = new SimpleDateFormat("MMMM dd, yyyy");
    private SimpleDateFormat formaterForMonth = new SimpleDateFormat("MMM");

    public SimpleDateFormat getFormaterForDate() {
        formaterForDate = new SimpleDateFormat("dd MMMM");
        return formaterForDate;
    }

    public SimpleDateFormat getFormaterForDateMMMMddYYYY() {
        formaterForDate = new SimpleDateFormat("MMMM dd, yyyy");
        return formaterForDate;
    }
    public SimpleDateFormat getFormaterForMonth() {
        formaterForDate = new SimpleDateFormat("MMM");
        return formaterForDate;
    }

    //setter amd getter for user
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Tags getTag() {
        return this.tag;
    }

    public List<Tags> getAllTags() {
        return tagsEJB.findAllTags();
    }
//GET UPCOMING Conference list

    public List<Session> getUpcomingTwoConferenceList() {
        List<Session> upcomingSessionList = new ArrayList<Session>();
        List<Session> finalUpcomingSessionList = new ArrayList<Session>();
        upcomingSessionList = sessionEJB.findUpcommingTwoSession();
        List<SessionPaper> sessionPaperList = new ArrayList<SessionPaper>();
        List<SessionParticipant> sessionParticipantList = new ArrayList<SessionParticipant>();
        for (Session session : upcomingSessionList) {
            sessionPaperList = sessionPaperEJB.findSessionPaperBySessionId(session);
            session.setSessionPapers(sessionPaperList);
            finalUpcomingSessionList.add(session);
        }
        return finalUpcomingSessionList;
    }
    
    //GET All Conference list

    public List<Session> getConferenceList() {
        List<Session> sessionList = new ArrayList<Session>();
        List<Session> finalSessionList = new ArrayList<Session>();
        sessionList = sessionEJB.findAllSession();
        List<SessionPaper> sessionPaperList = new ArrayList<SessionPaper>();
        List<SessionParticipant> sessionParticipantList = new ArrayList<SessionParticipant>();
        for (Session session : sessionList) {
            sessionPaperList = sessionPaperEJB.findSessionPaperBySessionId(session);
            session.setSessionPapers(sessionPaperList);
            finalSessionList.add(session);
        }
        return finalSessionList;
    }

    @PostConstruct
    private void init() {
        try {
            HttpSession session = request.getSession();
            Users loggedInUser = (Users) session.getAttribute("user");
            this.user = userEJB.findUserById(loggedInUser.getId());
            setUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
