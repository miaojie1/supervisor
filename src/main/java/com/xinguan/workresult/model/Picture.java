package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String pictureName;
    @Column
    private String pictureUrl;
    @Column
    private Date createDate;
    @ManyToOne
    @JoinColumn(name="picture_folder_id")
    @JsonIgnore
    private PictureFolder pictureFolder;
    @Version
    @Column
    private int version;
    @ManyToOne
    private Employee republisher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public PictureFolder getPictureFolder() {
        return pictureFolder;
    }

    public void setPictureFolder(PictureFolder pictureFolder) {
        this.pictureFolder = pictureFolder;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Employee getRepublisher() {
        return republisher;
    }

    public void setRepublisher(Employee republisher) {
        this.republisher = republisher;
    }
}
