/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findAllUser",query="select u from Users u"),
    @NamedQuery(name="findUserByRole", query="select u from Users u where u.role=:uRole"),
    @NamedQuery(name="findUserById", query="select u from Users u where u.id=:uId"),
    @NamedQuery(name="loginUser", query="select u from Users u where (u.userName=:userName AND u.password=:password) OR (u.email=:email AND u.password=:password)"),
    @NamedQuery(name="findUserByEmailOrUsername", query="select u from Users u where u.userName=:userName OR u.email=:email")
})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String email;
    private String phoneNumber;
    private String qualification;
    @Column(nullable = false)
    private String role;
    private boolean isApproved;
    private boolean isPublic;
    private boolean isActive;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String timeZone;
    private String address;
    @Column(nullable = false)
    private Date createdDate;
    private Date updatedDate;
    @OneToMany(mappedBy = "authorId", cascade = CascadeType.PERSIST)
    private List<Paper> paperList;
    @OneToMany(mappedBy = "participantId", cascade = CascadeType.PERSIST)
    private List<SessionParticipant> sessionParticipantList;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.PERSIST)
    private List<UserTags> userTags;
    @Transient
    private List<Long> tagList;

    public Users() {
    }

    public Users(Long id, String firstName, String lastName, String country, String email, String phoneNumber, String qualification, String role, boolean isApproved, boolean isPublic, boolean isActive, String userName, String password, String timeZone, String address, Date createdDate, Date updatedDate, List<Paper> paperList, List<SessionParticipant> sessionParticipantList, List<UserTags> userTags, List<Long> tagList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.qualification = qualification;
        this.role = role;
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
        this.userTags = userTags;
        this.tagList = tagList;
    }

    

    public Users(String firstName, String lastName, String country, String email, String phoneNumber, String qualification, String role, boolean isApproved, boolean isPublic, boolean isActive, String userName, String password, String timeZone, String address, Date createdDate, Date updatedDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.qualification = qualification;
        this.role = role;
        this.isApproved = isApproved;
        this.isPublic = isPublic;
        this.isActive = isActive;
        this.userName = userName;
        this.password = password;
        this.timeZone = timeZone;
        this.address = address;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
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

    public List<UserTags> getUserTags() {
        return userTags;
    }

    public void setUserTags(List<UserTags> userTags) {
        this.userTags = userTags;
    }

    public List<Long> getTagList() {
        return tagList;
    }

    public void setTagList(List<Long> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", country=" + country + ", email=" + email + ", phoneNumber=" + phoneNumber + ", qualification=" + qualification + ", role=" + role + ", isApproved=" + isApproved + ", isPublic=" + isPublic + ", isActive=" + isActive + ", userName=" + userName + ", password=" + password + ", timeZone=" + timeZone + ", address=" + address + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", paperList=" + paperList + ", sessionParticipantList=" + sessionParticipantList + ", userTags=" + userTags + '}';
    }
   
    
}
