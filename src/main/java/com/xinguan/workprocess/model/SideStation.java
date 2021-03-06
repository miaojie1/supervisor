package com.xinguan.workprocess.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.Picture;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 旁站
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SideStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //发起人
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee sponsor;

    //修改人
    @ManyToOne
    private Employee modifier;

    //项目
    @ManyToOne
    private Project project;

    // 部位
    @Column
    private String part;

    //照片
    @Column
    @OneToMany
    private List<Picture> pictureList;

    //创建时间
    @Column
    private Date createDate;

    //旁站时间
    @Column
    private Date sideStationDate;

    //简要内容
    @Column
    private String brief;

    //处理问题
    @Column
    private String problems;

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

    @Column
    private String processId;

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Employee getSponsor() {
        return sponsor;
    }

    public void setSponsor(Employee sponsor) {
        this.sponsor = sponsor;
    }

    public Employee getModifier() {
        return modifier;
    }

    public void setModifier(Employee modifier) {
        this.modifier = modifier;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
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

    public Date getSideStationDate() {
        return sideStationDate;
    }

    public void setSideStationDate(Date sideStationDate) {
        this.sideStationDate = sideStationDate;
    }
}
