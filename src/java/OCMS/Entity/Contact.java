/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findAllContact",query="select c from Contact c")
})
public class Contact implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private Date createdDate;

    public Contact() {
    }

    public Contact(String fullName, String email, String subject, String message, Date createdDate) {
        this.fullName = fullName;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.createdDate = createdDate;
    }

    public Contact(Long id, String fullName, String email, String subject, String message, Date createdDate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Contact{" + "id=" + id + ", fullName=" + fullName + ", email=" + email + ", subject=" + subject + ", message=" + message + ", createdDate=" + createdDate + '}';
    }

    
}
