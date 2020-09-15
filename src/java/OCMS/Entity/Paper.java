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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.servlet.http.Part;

/**
 *
 * @author SabinRegmi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findAllPaper", query = "select p from Paper p ORDER BY p.createdDate")
    ,
    @NamedQuery(name = "findPaperByTitle", query = "select p from Paper p where p.paperTitle=:pTitle ORDER BY p.createdDate DESC")
    ,
    @NamedQuery(name = "findPaperById", query = "select p from Paper p where p.paperId=:pPaperId")
   
    ,
    @NamedQuery(name = "findPaperByAuthorId", query = "select p from Paper p where p.authorId.id=:authorId ORDER BY p.createdDate DESC")
    ,
    @NamedQuery(name = "numberOfPaperSubmittedByAuthor", query = "select p from Paper p where p.authorId.id=:authorId"),
//select p, t.name from Paper  p LEFT JOIN PaperTags pt ON p.paperId = pt.paperId LEFT JOIN Tags t ON pt.tagId = t.id where p.authorId.id=:authorId
    @NamedQuery(name = "findPaperByTagsForUser", query = "select p from Paper p, Users u where u.id=:pId ORDER BY p.createdDate DESC")
})
public class Paper implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paperId;
    @Column(nullable = false)
    private String paperTitle;
    @Column(nullable = false)
    private String paperUrl;
    @Column(nullable = false)
    private Date createdDate;
    private Date updatedDate;
    private String paperAbstract;
    @ManyToOne
    private Users authorId;
    private String publisher;
    @OneToMany(mappedBy = "paperId", cascade = CascadeType.PERSIST)
    private List<PaperTags> paperTags;
    @Transient
    private Part pdfFile;
    @Transient
    private List<Long> tagList;

    public Paper() {
    }

    public Paper(String paperTitle, String paperUrl, Date createdDate, Date updatedDate, String paperAbstract, Users authorId, String publisher) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.paperUrl = paperUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.paperAbstract = paperAbstract;
        this.authorId = authorId;
        this.publisher = publisher;
    }

    public Paper(String paperTitle, String paperUrl, Date createdDate, Date updatedDate, String paperAbstract, Users authorId, String publisher, List<PaperTags> paperTags,List<Long> tagList) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.paperUrl = paperUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.paperAbstract = paperAbstract;
        this.authorId = authorId;
        this.publisher = publisher;
        this.paperTags = paperTags;
        this.tagList=tagList;
    }
    

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
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


    public List<PaperTags> getPaperTags() {
        return paperTags;
    }

    public void setPaperTags(List<PaperTags> paperTags) {
        this.paperTags = paperTags;
    }

    public Part getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(Part pdfFile) {
        this.pdfFile = pdfFile;
    }

    public List<Long> getTagList() {
        return tagList;
    }

    public void setTagList(List<Long> tagList) {
        this.tagList = tagList;
    }
    

    @Override
    public String toString() {
        return "Paper{" + "paperId=" + paperId + ", paperTitle=" + paperTitle + ", paperUrl=" + paperUrl + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", paperAbstract=" + paperAbstract + ", authorId=" + authorId + ", publisher=" + publisher + '}';
    }

}
