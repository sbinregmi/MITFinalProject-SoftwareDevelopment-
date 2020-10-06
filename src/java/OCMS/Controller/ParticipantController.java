/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.PaperEJB;
import OCMS.EJB.PaperTagsEJB;
import OCMS.EJB.SessionEJB;
import OCMS.EJB.SessionPaperEJB;
import OCMS.EJB.SessionParticipantEJB;
import OCMS.EJB.TagsEJB;
import OCMS.EJB.UserEJB;
import OCMS.Entity.Paper;
import OCMS.Entity.Session;
import OCMS.Entity.SessionPaper;
import OCMS.Entity.SessionParticipant;
import OCMS.Entity.Users;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SabinRegmi
 */
@ManagedBean(name = "participantController")
@RequestScoped
public class ParticipantController {

    @EJB
    private UserEJB userEJB;
    @EJB
    private SessionEJB sessionEJB;
    @EJB
    private SessionParticipantEJB sessionParticipantEJB;
    @EJB
    private SessionPaperEJB sessionPaperEJB;
    @Inject
    HttpServletRequest request;
    private Users user = new Users();
    private Paper paper = new Paper();
    private SessionParticipant sessionParticipant = new SessionParticipant();
    private Long numberOfConferenceAccepted;
    private Long numberOfConferenceRequested;
    private Long numberOfConferenceRejected;
    private Long numberOfConferenceJoined;
private SimpleDateFormat formaterForDateTime = new SimpleDateFormat("MMM dd, yyyy hh:mm");

    

    public SimpleDateFormat getFormaterForDateTime() {
        formaterForDateTime = new SimpleDateFormat("MMM dd, yyyy hh:mm");
        return formaterForDateTime;
    }
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Long getNumberOfConferenceRequested() {
        return this.numberOfConferenceRequested;
    }

    public void setNumberOfConferenceRequested(Long numberOfConferenceRequested) {
        this.numberOfConferenceRequested = numberOfConferenceRequested;
    }

    public Long getNumberOfConferenceRejected() {
        return this.numberOfConferenceRejected;
    }

    public void setNumberOfConferenceRejected(Long numberOfConferenceRejected) {
        this.numberOfConferenceRejected = numberOfConferenceRejected;
    }

    public Long getNumberOfConferenceJoined() {
        return this.numberOfConferenceJoined;
    }

    public void setNumberOfConferenceJoined(Long numberOfConferenceJoined) {
        this.numberOfConferenceJoined = numberOfConferenceJoined;
    }

    public Long getNumberOfConferenceAccepted() {
        return this.numberOfConferenceAccepted;
    }

    public void setNumberOfConferenceAccepted(Long numberOfConferenceAccepted) {
        this.numberOfConferenceAccepted = numberOfConferenceAccepted;
    }

    //To display in dashboard
    public void getNumberOfConference() {
        List<SessionParticipant> allSessionByPartcipant = sessionParticipantEJB.findSessionParticipantByParticipantId(user);
        Long acceptedSession = allSessionByPartcipant.stream().filter(f -> f.getStatus().equals(OCMS.ModelData.Enum.Status.ACCEPTED)).count();
        Long pendingSession = allSessionByPartcipant.stream().filter(f -> f.getStatus().equals(OCMS.ModelData.Enum.Status.PENDING)).count();
        Long rejectedSession = allSessionByPartcipant.stream().filter(f -> f.getStatus().equals(OCMS.ModelData.Enum.Status.REJECTED)).count();
        Long joinedSession = allSessionByPartcipant.stream().filter(f -> f.getStatus().equals(OCMS.ModelData.Enum.Status.JOINED)).count();
        setNumberOfConferenceRequested(pendingSession);
        setNumberOfConferenceRejected(rejectedSession);
        setNumberOfConferenceJoined(joinedSession);
        setNumberOfConferenceAccepted(acceptedSession);
    }

    //Conference list
    public List<Session> getConferenceList() {
        List<Session> upcomingSessionList = new ArrayList<Session>();
        List<Session> finalUpcomingSessionList = new ArrayList<Session>();
        upcomingSessionList = sessionEJB.findUpcommingSession();
        List<SessionPaper> sessionPaperList = new ArrayList<SessionPaper>();
        List<SessionParticipant> sessionParticipantList = new ArrayList<SessionParticipant>();
        for (Session session : upcomingSessionList) {
            sessionPaperList = sessionPaperEJB.findSessionPaperBySessionId(session);
            session.setSessionPapers(sessionPaperList);
            sessionParticipantList=sessionParticipantEJB.findSessionParticipantBySessionId(session);
            session.setSessionParticipants(sessionParticipantList);
            finalUpcomingSessionList.add(session);
        }
        return finalUpcomingSessionList;
    }
    
    //POST: SUBMIT REQUEST TO JOIN SESSION
     public String submitRequestToJoinSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("sessionIdSessionRequest")) {
                Long sessionId = Long.parseLong(requestParamMap.get("sessionIdSessionRequest"));
                Session session = sessionEJB.findSessionById(sessionId);
                sessionParticipant.setParticipantId(user);
                sessionParticipant.setSessionId(session);
                sessionParticipant.setStatus(OCMS.ModelData.Enum.Status.PENDING);
                sessionParticipantEJB.createSessionParticipant(sessionParticipant);
                context.addMessage("success", new FacesMessage("Your request to join '" + session.getSessionName()+"' is in pending."));
                return "joinConference";
            } else {
                context.addMessage("error", new FacesMessage("Internal server error."));
                return "joinConference";
            }
        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "joinConference";
        }
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
