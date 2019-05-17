package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.workprocess.model.Project;

import javax.persistence.*;
import java.util.Date;

/**
 * 监理日志记录
 * @author MJ
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SupervisionLogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 记录发生时间
     */
    @Column
    private Date recordDate;

    /**
     * 对应项目
     */
    @ManyToOne(targetEntity = Project.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Project project;

    /**
     * 记录内容
     */
    @Column
    private String recordContent;
    /**
     * 对应的监理日志
     */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH}, optional = false,fetch = FetchType.LAZY)
    @JsonIgnore
    //可选属性optional=false,supervisionLog。删除记录，不影响日志
    //设置在 记录 表中的关联字段(外键)
    @JoinColumn(name="supervisionLog_id")
    private SupervisionLog supervisionLog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public SupervisionLog getSupervisionLog() {
        return supervisionLog;
    }

    public void setSupervisionLog(SupervisionLog supervisionLog) {
        this.supervisionLog = supervisionLog;
    }
}
