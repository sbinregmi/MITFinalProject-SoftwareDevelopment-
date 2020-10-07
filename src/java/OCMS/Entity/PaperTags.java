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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findTagsByPaperId", query = "select pt from PaperTags pt where pt.paperId.paperId=:paperId"),
    @NamedQuery(name = "findPaperTagsByTagId", query = "select pt from PaperTags pt where pt.tagId.id=:tagId"),
     @NamedQuery(name = "removeTagsByPaperId", query = "Delete FROM PaperTags pt where pt.paperId.paperId=:paperId")
})
public class PaperTags implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Paper paperId;
    @ManyToOne
    private Tags tagId;

    public PaperTags() {
    }

    public PaperTags(Long id, Paper paperId, Tags tagId) {
        this.id = id;
        this.paperId = paperId;
        this.tagId = tagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paper getPaperId() {
        return paperId;
    }

    public void setPaperId(Paper paperId) {
        this.paperId = paperId;
    }

    public Tags getTagId() {
        return tagId;
    }

    public void setTagId(Tags tagId) {
        this.tagId = tagId;
    }
    
    
    
}
