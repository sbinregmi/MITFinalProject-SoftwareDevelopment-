/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.PaperEJB;
import OCMS.EJB.SessionEJB;
import OCMS.EJB.SessionPaperEJB;
import OCMS.EJB.SessionParticipantEJB;
import OCMS.EJB.UserEJB;
import OCMS.Entity.Paper;
import OCMS.Entity.Session;
import OCMS.Entity.SessionPaper;
import OCMS.Entity.SessionParticipant;
import OCMS.Entity.Users;
import OCMS.ModelData.Enum.Role;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author SabinRegmi
 */
@ManagedBean(name = "organizerController")
@RequestScoped
public class OrganizerController {

    @EJB
    private UserEJB userEJB;
    @EJB
    private SessionEJB sessionEJB;
    @EJB
    private PaperEJB paperEJB;
    @EJB
    private SessionParticipantEJB sessionParticipantEJB;
    @EJB
    private SessionPaperEJB sessionPaperEJB;
    @Inject
    HttpServletRequest request;
    private Users user = new Users();
    private Session session = new Session();
    private SessionPaper sessionPaper = new SessionPaper();
    private SessionParticipant sessionParticipant = new SessionParticipant();
    private Long numberOfPaperSubmission;
    private Long numberOfParticipant;
    private Long numberOfConference;

    private Long sessionId;

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

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Long getNumberOfPaperSubmission() {
        return this.numberOfPaperSubmission;
    }

    public void setNumberOfPaperSubmission(Long numberOfPaperSubmission) {
        this.numberOfPaperSubmission = numberOfPaperSubmission;
    }

    public Long getNumberOfParticipant() {
        return this.numberOfParticipant;
    }

    public void setNumberOfParticipant(Long numberOfParticipant) {
        this.numberOfParticipant = numberOfParticipant;
    }

    public Long getNumberOfConference() {
        return this.numberOfConference;
    }

    public void setNumberOfConference(Long numberOfConference) {
        this.numberOfConference = numberOfConference;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    //GET: STATISTIC To display in dashboard
    public void getStatisticForDashboard() {
        numberOfParticipant = sessionParticipantEJB.countAllSessionParticipant();
        setNumberOfParticipant(numberOfParticipant);
        numberOfPaperSubmission = sessionPaperEJB.countAllSessionPaper();
        setNumberOfPaperSubmission(numberOfPaperSubmission);
        numberOfConference = sessionEJB.countAllSession();
        setNumberOfConference(numberOfConference);
    }

    //GET: Conference list
    public List<Session> getConferenceList() {
        List<Session> upcomingSessionList = new ArrayList<Session>();
        List<Session> finalUpcomingSessionList = new ArrayList<Session>();
        upcomingSessionList = sessionEJB.findAllSession();
        List<SessionPaper> sessionPaperList = new ArrayList<SessionPaper>();
        List<SessionParticipant> sessionParticipantList = new ArrayList<SessionParticipant>();
        for (Session session : upcomingSessionList) {
            sessionPaperList = sessionPaperEJB.findSessionPaperBySessionId(session);
            session.setSessionPapers(sessionPaperList);
            sessionParticipantList = sessionParticipantEJB.findSessionParticipantBySessionId(session);
            session.setSessionParticipants(sessionParticipantList);
            finalUpcomingSessionList.add(session);
        }
        return finalUpcomingSessionList;
    }

    //GET: Conference By ID
    public Session getConferenceById(Long sessionId) {
        Session session = sessionEJB.findSessionById(sessionId);
        List<SessionPaper> sessionPaperList = new ArrayList<SessionPaper>();
        List<SessionParticipant> sessionParticipantList = new ArrayList<SessionParticipant>();
        sessionPaperList = sessionPaperEJB.findSessionPaperBySessionId(session);
        session.setSessionPapers(sessionPaperList);
        sessionParticipantList = sessionParticipantEJB.findSessionParticipantBySessionId(session);
        session.setSessionParticipants(sessionParticipantList);
        return session;
    }

    //POST: CREATE A SESSION
    public String createSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Date dateNow = new Date();
            String sessionDateStr = session.getSessionDateTimeStr();
            Date sessionDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sessionDateStr);
            session.setSessionDateTime(sessionDate);
            session.setCreatedDate(dateNow);
            boolean imageUploaded = false;
            Part image = session.getImage();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            String imageDir = File.separator + "resource" + File.separator + "conference" + File.separator;
            File dir = new File(imageDir);
            if (!dir.exists()) {
                dir.mkdirs(); //create directory /images/tags if not exist
            }
            String extensionOfImage = "";
            int i = image.getSubmittedFileName().lastIndexOf('.');
            if (i > 0) {
                extensionOfImage = image.getSubmittedFileName().substring(i + 1);
            }
            String imageUrl = imageDir + session.getSessionName() + "." + extensionOfImage;
            String realPath = servletContext.getRealPath("") + imageUrl;
            File outputFile = new File(realPath);
            int num = 1;
            while (outputFile.exists()) {
                imageUrl = imageDir + session.getSessionName() + num + "." + extensionOfImage;
                outputFile = new File(servletContext.getRealPath("") + imageUrl);
                num++;
            }
            session.setImageUrl(servletContext.getContextPath() + imageUrl);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            if (session.getImage().getSize() > 0) {
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
            sessionEJB.createSession(session);
            session = new Session();
            context.addMessage("success", new FacesMessage("New conference created."));
            return "create";
        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "create";
        }
    }

    //POST: UPDATE A SESSION
    public String updateSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            Date dateNow = new Date();
            String sessionDateStr = session.getSessionDateTimeStr();
            Session sessionOlder=sessionEJB.findSessionById(session.getSessionId());
            if(!sessionDateStr.isEmpty() || sessionDateStr!=null){
                Date sessionDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sessionDateStr);
            sessionOlder.setSessionDateTime(sessionDate);
            }
            sessionOlder.setUpdatedDate(dateNow);
            boolean imageUploaded = false;
            Part image = session.getImage();
            String oldImageUrl = sessionOlder.getImageUrl();
            if (!oldImageUrl.isEmpty() && oldImageUrl != null) {
                oldImageUrl = oldImageUrl.replace("/OCMS\\", "/");
                oldImageUrl = oldImageUrl.replace("\\", "/");
                File previousFile = new File(servletContext.getRealPath(oldImageUrl));
                boolean result = Files.deleteIfExists(previousFile.toPath());
            }

            String imageDir = File.separator + "resource" + File.separator + "conference" + File.separator;
            File dir = new File(imageDir);
            if (!dir.exists()) {
                dir.mkdirs(); //create directory /images/tags if not exist
            }
            String extensionOfImage = "";
            int i = image.getSubmittedFileName().lastIndexOf('.');
            if (i > 0) {
                extensionOfImage = image.getSubmittedFileName().substring(i + 1);
            }
            String imageUrl = imageDir + session.getSessionName() + "." + extensionOfImage;
            String realPath = servletContext.getRealPath("") + imageUrl;
            File outputFile = new File(realPath);
            int num = 1;
            while (outputFile.exists()) {
                imageUrl = imageDir + session.getSessionName() + num + "." + extensionOfImage;
                outputFile = new File(servletContext.getRealPath("") + imageUrl);
                num++;
            }
            sessionOlder.setImageUrl(servletContext.getContextPath() + imageUrl);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            if (session.getImage().getSize() > 0) {
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
            sessionEJB.updateSession(sessionOlder);
            session = new Session();
            context.addMessage("success", new FacesMessage("Conference is updated."));
            return "update";
        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "update";
        }
    }

    //GET: SESSION DETAIL BY ID
    public String sessionDetail() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("id")) {
                Long id = Long.parseLong(requestParamMap.get("id"));
                Session session = sessionEJB.findSessionById(id);
                setSession(session);
                return "conference/update.xhtml";
            } else {
                context.addMessage("error", new FacesMessage("Internal server error."));
                return "manageConference.xhtml";
            }
        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "manageConference.xhtml";
        }
    }

    //POST: DELETE A SESSION BY SESSION ID
    public String deleteSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("sessionIdToBeDelete")) {
                Long id = Long.parseLong(requestParamMap.get("sessionIdToBeDelete"));
                Session session = sessionEJB.findSessionById(id);
                List<SessionPaper> sessionPaperList = sessionPaperEJB.findSessionPaperBySessionId(session);
                for (SessionPaper sessionPaper : sessionPaperList) {
                    sessionPaperEJB.deleteSessionPaper(sessionPaper);
                }
                List<SessionParticipant> sessionParticipantList = sessionParticipantEJB.findSessionParticipantBySessionId(session);
                for (SessionParticipant sessionParticipant : sessionParticipantList) {
                    sessionParticipantEJB.deleteSessionParticipant(sessionParticipant);
                }
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                boolean result = false;
                String imageUrl = session.getImageUrl();
                if (!imageUrl.isEmpty() && imageUrl != null) {
                    imageUrl = imageUrl.replace("/OCMS\\", "/");
                    imageUrl = imageUrl.replace("\\", "/");
                    File previousFile = new File(servletContext.getRealPath(imageUrl));
                    result = Files.deleteIfExists(previousFile.toPath());
                }
                sessionEJB.deleteSession(session);
                context.addMessage("success", new FacesMessage("Session is deleted successfully."));
                return "manageConference";
            } else {
                context.addMessage("error", new FacesMessage("Internal server error."));
                return "manageConference";
            }
        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "manageConference";
        }

    }

    //POST:ACCEPT A PAPER FOR A SESSION
    public String submitAcceptPaperForSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("sessionPaperIdToAccept")) {
                Long sessionPaperIdToAccept = Long.parseLong(requestParamMap.get("sessionPaperIdToAccept"));
                sessionPaper = sessionPaperEJB.findSessionPaperById(sessionPaperIdToAccept);
                sessionPaper.setStatus(OCMS.ModelData.Enum.Status.ACCEPTED);
                sessionPaper = sessionPaperEJB.updateSessionPaper(sessionPaper);
                context.addMessage("success", new FacesMessage("Paper " + sessionPaper.getPaperId().getPaperTitle() + " for conference " + sessionPaper.getSessionId().getSessionName() + " is accepted."));
//                 context.addMessage("success", new FacesMessage("Paper is accepted."));
                setSessionId(sessionPaper.getSessionId().getSessionId());
                return "paperRequest";
            } else {
                context.addMessage("error", new FacesMessage("Conference not found."));
                return "paperRequest";
            }

        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "manageConference";
        }
    }

    //POST:Reeject A PAPER FOR A SESSION
    public String submitRejectPaperForSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("sessionPaperIdToReject")) {
                Long sessionPaperIdToReject = Long.parseLong(requestParamMap.get("sessionPaperIdToReject"));
                sessionPaper = sessionPaperEJB.findSessionPaperById(sessionPaperIdToReject);
                sessionPaper.setStatus(OCMS.ModelData.Enum.Status.REJECTED);
                sessionPaper = sessionPaperEJB.updateSessionPaper(sessionPaper);
                context.addMessage("success", new FacesMessage("Paper " + sessionPaper.getPaperId().getPaperTitle() + " for conference " + sessionPaper.getSessionId().getSessionName() + " is rejected."));
                //request.setAttribute("id", sessionPaperIdToReject);
                setSessionId(sessionPaper.getSessionId().getSessionId());
                return "paperRequest";
            } else {
                context.addMessage("error", new FacesMessage("Internal server error."));
                return "paperRequest";
            }

        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "manageConference";
        }
    }

    //POST:ACCEPT A PARTICIPANT FOR A SESSION
    public String submitAcceptParticipantForSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("sessionParticipantIdToAccept")) {
                Long sessionParticipantIdToAccept = Long.parseLong(requestParamMap.get("sessionParticipantIdToAccept"));
                sessionParticipant = sessionParticipantEJB.findSessionParticipantById(sessionParticipantIdToAccept);
                sessionParticipant.setStatus(OCMS.ModelData.Enum.Status.ACCEPTED);
                sessionParticipant = sessionParticipantEJB.updateSessionParticipant(sessionParticipant);
                context.addMessage("success", new FacesMessage("Participant " + sessionParticipant.getParticipantId().getFirstName() + " " + sessionParticipant.getParticipantId().getLastName() + " for conference " + sessionPaper.getSessionId().getSessionName() + " is accepted."));
                setSessionId(sessionParticipant.getSessionId().getSessionId());
                return "participantRequest";
            } else {
                context.addMessage("error", new FacesMessage("Conference not found."));
                return "participantRequest";
            }

        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "manageConference";
        }
    }

    //POST:Reject A PARTICIPANT FOR A SESSION
    public String submitRejectParticipantForSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
            if (requestParamMap.containsKey("sessionParticipantIdToReject")) {
                Long sessionParticipantIdToReject = Long.parseLong(requestParamMap.get("sessionParticipantIdToReject"));
                sessionParticipant = sessionParticipantEJB.findSessionParticipantById(sessionParticipantIdToReject);
                sessionParticipant.setStatus(OCMS.ModelData.Enum.Status.REJECTED);
                sessionParticipant = sessionParticipantEJB.updateSessionParticipant(sessionParticipant);
                context.addMessage("success", new FacesMessage("Participant " + sessionParticipant.getParticipantId().getFirstName() + " " + sessionParticipant.getParticipantId().getLastName()
                        + " for conference " + sessionPaper.getSessionId().getSessionName() + " is rejected."));
                //request.setAttribute("id", sessionParticipantIdToReject);
                setSessionId(sessionParticipant.getSessionId().getSessionId());
                return "participantRequest";
            } else {
                context.addMessage("error", new FacesMessage("Internal server error."));
                return "paperRequest";
            }

        } catch (Exception ex) {
            context.addMessage("error", new FacesMessage("Internal server error."));
            return "paperRequest";
        }
    }

    //GET: LIST OF ALL PARTICIPANT
    public List<Users> getParticipantList() {
        List<Users> participantList = userEJB.findUserByRole(Role.Participant.name());
        return participantList;
    }

    //GET: LIST OF ALL PAPER
    public List<Paper> getPaperList() {
        List<Paper> paper = paperEJB.findAllPaper();
        return paper;
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
