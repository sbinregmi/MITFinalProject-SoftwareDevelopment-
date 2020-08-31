/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author SabinRegmi
 */
@Entity
public class UserTags implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @ManyToOne
    private Users userId;
    
    
    @ManyToOne
    private Tags userTagId;

    public UserTags() {
    }

    public UserTags(Long id, Users userId, Tags userTagId) {
        this.id = id;
        this.userId = userId;
        this.userTagId = userTagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Tags getUserTagId() {
        return userTagId;
    }

    public void setUserTagId(Tags userTagId) {
        this.userTagId = userTagId;
    }
    
    
}
