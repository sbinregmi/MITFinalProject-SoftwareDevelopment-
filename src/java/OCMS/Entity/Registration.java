/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import OCMS.ModelData.Enum;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.TimeZone;
import javax.faces.view.facelets.Tag;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.security.enterprise.credential.Password;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findAllUser",query="select r from Registration r"),
    @NamedQuery(name="findUserByRole", query="select r from Registration r where r.role=:rRole"),
    @NamedQuery(name="findUserById", query="select r from Registration r where r.id=:rId"),
    @NamedQuery(name="loginUser", query="select r from Registration r where r.userName=:rUserName AND r.password=:rPassword")
    
})
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String interestedGroups;
    private String phoneNumber;
    private String qualification;
    private String role;
    private String profilePicture;
    private boolean isApproved;
    private boolean isPublic;
    private boolean isActive;
    private String userName;
    private String password;
    private TimeZone timeZone;
    private String address;
    private Date createdDate;
    private Date updatedDate;
    @OneToMany(mappedBy = "authorId")
    private List<Paper> paperList;
    @OneToMany(mappedBy = "participantId")
    private List<SessionParticipant> sessionParticipantList;
    @OneToMany(mappedBy = "paperId")
    private List<UserTags> userTags;

    public Registration() {
    }

    public Registration(Long id, String firstName, String lastName, Date dateOfBirth, String email, String interestedGroups, String phoneNumber, String qualification, String role, String profilePicture, boolean isApproved, boolean isPublic, boolean isActive, String userName, String password, TimeZone timeZone, String address, Date createdDate, Date updatedDate, List<Paper> paperList, List<SessionParticipant> sessionParticipantList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.interestedGroups = interestedGroups;
        this.phoneNumber = phoneNumber;
        this.qualification = qualification;
        this.role = role;
        this.profilePicture = profilePicture;
        this.isApproved = isApproved;
        this.isPublic = isPublic;
        this.isActive = isActive;
        this.userName = userName;
        this.password = password;
        this.timeZone = timeZone;
        this.address = address;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.paperList = paperList;
        this.sessionParticipantList = sessionParticipantList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterestedGroups() {
        return interestedGroups;
    }

    public void setInterestedGroups(String interestedGroups) {
        this.interestedGroups = interestedGroups;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Paper> getPaperList() {
        return paperList;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
    }

    public List<SessionParticipant> getSessionParticipantList() {
        return sessionParticipantList;
    }

    public void setSessionParticipantList(List<SessionParticipant> sessionParticipantList) {
        this.sessionParticipantList = sessionParticipantList;
    }

    @Override
    public String toString() {
        return "Registration{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", email=" + email + ", interestedGroups=" + interestedGroups + ", phoneNumber=" + phoneNumber + ", qualification=" + qualification + ", role=" + role + ", profilePicture=" + profilePicture + ", isApproved=" + isApproved + ", isPublic=" + isPublic + ", isActive=" + isActive + ", userName=" + userName + ", password=" + password + ", timeZone=" + timeZone + ", address=" + address + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", paperList=" + paperList + ", sessionParticipantList=" + sessionParticipantList + '}';
    }

    
    
}
