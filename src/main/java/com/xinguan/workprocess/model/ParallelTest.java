package com.xinguan.workprocess.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * @平行检验
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParallelTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //检查时间
    @Column
    private Date testDate;

    // 部位
    @Column
    private String part;

    //简要内容
    @Column
    private String brief;

    //是否提交，0是保存，1是提交
    @Column
    private Integer isSubmit;

    //可修改的最低等级
    @Column
    private Integer originRank;

    //审核状态
    @Column
    private String auditStatus;
    @Transient
    private Boolean needAudit;
    @Transient
    private String taskId;
    /**
     * 审核人审核
     */
    @OneToOne
    private EmployeeAudit employeeAudit;
    /**
     * 总监审核
     *
     */
    @OneToOne
    private EmployeeAudit majorAudit;

    //检查人员
    @ManyToOne
    private Employee sponsor;

    //项目
    @ManyToOne
    private Project project;

    @Column
    private String processId;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean getNeedAudit() {
        return needAudit;
    }

    public void setNeedAudit(Boolean needAudit) {
        this.needAudit = needAudit;
    }

    public EmployeeAudit getEmployeeAudit() {
        return employeeAudit;
    }

    public void setEmployeeAudit(EmployeeAudit employeeAudit) {
        this.employeeAudit = employeeAudit;
    }

    public EmployeeAudit getMajorAudit() {
        return majorAudit;
    }

    public void setMajorAudit(EmployeeAudit majorAudit) {
        this.majorAudit = majorAudit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public Integer getOriginRank() {
        return originRank;
    }

    public void setOriginRank(Integer originRank) {
        this.originRank = originRank;
    }

    public Employee getSponsor() {
        return sponsor;
    }

    public void setSponsor(Employee sponsor) {
        this.sponsor = sponsor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
