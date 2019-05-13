package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String documentName;
    @Column
    private String documentUrl;
    @Column
    private Date createDate;
    @ManyToOne
    private DocumentFolder documentFolder;
    @Version
    @Column
    private int version;
    @ManyToOne
    private DocumentCategory documentCategory;
    @ManyToOne
    private Employee uploader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public DocumentFolder getDocumentFolder() {
        return documentFolder;
    }

    public void setDocumentFolder(DocumentFolder documentFolder) {
        this.documentFolder = documentFolder;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DocumentCategory getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(DocumentCategory documentCategory) {
        this.documentCategory = documentCategory;
    }

    public Employee getUploader() {
        return uploader;
    }

    public void setUploader(Employee uploader) {
        this.uploader = uploader;
    }
}