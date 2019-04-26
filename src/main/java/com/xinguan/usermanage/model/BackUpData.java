package com.xinguan.usermanage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author zhangzhan
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BackUpData {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String filePath;
    @Column
    private Date date;
    @Column
    private String backUpType;
    @Column
    private String fileSize;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBackUpType() {
        return backUpType;
    }

    public void setBackUpType(String backUpType) {
        this.backUpType = backUpType;
    }

    public enum BackUpType {
        SYSTEM_TRIGGER, USER_TRIGGER
    }

}
