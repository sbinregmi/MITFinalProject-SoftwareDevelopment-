/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import OCMS.ModelData.Enum;
import java.util.List;
import javax.faces.view.facelets.Tag;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findAllPaper", query = "select p from Paper p")
    ,
    @NamedQuery(name = "findPaperByTitle", query = "select p from Paper p where p.paperTitle=:pTitle")
    ,
    @NamedQuery(name = "findPaperById", query = "select p from Paper p where p.paperId=:pPaperId")
    ,
    @NamedQuery(name = "findPaperBytopic", query = "select p from Paper p where p.topic=:pTopic")
    ,
    @NamedQuery(name = "findPaperByAuthorId", query = "select p from Paper p where p.authorId=:pAuthorId")
    ,
    @NamedQuery(name = "findPaperByTagsForUser", query = "select p from Paper p, Users u where u.id=:pId")
})
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String paperId;
    @Column(nullable = false)
    private String paperTitle;
    @Column(nullable = false)
    private Enum.PaperTopic topic;
    @Column(nullable = false)
    private String paperUrl;
    @Column(nullable = false)
    private Date createdDate;
    private Date updatedDate;
    private String paperAbstract;
    @ManyToOne
    private Users authorId;
    private String publisher;
    private String tags;
    @OneToMany(mappedBy = "paperId")
    private List<PaperTags> paperTags;

    public Paper() {
    }

    public Paper(String paperId, String paperTitle, Enum.PaperTopic topic, String paperUrl, Date createdDate, Date updatedDate, String paperAbstract, Users authorId, String publisher, String tags) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.topic = topic;
        this.paperUrl = paperUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.paperAbstract = paperAbstract;
        this.authorId = authorId;
        this.publisher = publisher;
        this.tags = tags;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public Enum.PaperTopic getTopic() {
        return topic;
    }

    public void setTopic(Enum.PaperTopic topic) {
        this.topic = topic;
    }

    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
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

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public Users getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Users authorId) {
        this.authorId = authorId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Paper{" + "paperId=" + paperId + ", paperTitle=" + paperTitle + ", topic=" + topic + ", paperUrl=" + paperUrl + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", paperAbstract=" + paperAbstract + ", authorId=" + authorId + ", publisher=" + publisher + ", tags=" + tags + '}';
    }

}
