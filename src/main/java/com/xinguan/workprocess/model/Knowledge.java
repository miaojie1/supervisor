package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangzhan
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Knowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String fileName;
    @Column
    private String fileUrl;
    @Column
    private Date createDate;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private FileFolder fileFolder;
    @Version
    @Column
    private int version;
    @ManyToOne
    private FileCategory fileCategory;
    @ManyToOne
    private Employee republisher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public FileFolder getFileFolder() {
        return fileFolder;
    }

    public void setFileFolder(FileFolder fileFolder) {
        this.fileFolder = fileFolder;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }

    public Employee getRepublisher() {
        return republisher;
    }

    public void setRepublisher(Employee republisher) {
        this.republisher = republisher;
    }
}
