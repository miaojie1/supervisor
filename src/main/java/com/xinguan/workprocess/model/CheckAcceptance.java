package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.Picture;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//检查验收
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CheckAcceptance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String acceptanceName;
    @Column
    private String part;

    //创建人
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee creater;

    // 所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
    @ManyToOne
    private Project project;
    //验收时间
    @Column
    private Date receptionDate;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @Column
    private String remark;
    @Column
    private Integer originRank;
    // 判断保存还是提交 0是保存 1是提交
    @Column
    private Integer isSubmit;
    //流程图
    @OneToMany
    private List<Picture> pictureList;
    //审核
    @OneToOne
    private EmployeeAudit employeeAudit;

    @Column
    private String auditStatus;
    @Column
    @Version
    private int version;

    @Transient
    private Boolean needAudit;
    @Transient
    private String taskId;
    /**
     * 总监审核
     *
     */
    @OneToOne
    private EmployeeAudit majorAudit;
    @Column
    private String processId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcceptanceName() {
        return acceptanceName;
    }

    public void setAcceptanceName(String acceptanceName) {
        this.acceptanceName = acceptanceName;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Employee getCreater() {
        return creater;
    }

    public void setCreater(Employee creater) {
        this.creater = creater;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOriginRank() {
        return originRank;
    }

    public void setOriginRank(Integer originRank) {
        this.originRank = originRank;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public EmployeeAudit getEmployeeAudit() {
        return employeeAudit;
    }

    public void setEmployeeAudit(EmployeeAudit employeeAudit) {
        this.employeeAudit = employeeAudit;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public EmployeeAudit getMajorAudit() {
        return majorAudit;
    }

    public void setMajorAudit(EmployeeAudit majorAudit) {
        this.majorAudit = majorAudit;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
