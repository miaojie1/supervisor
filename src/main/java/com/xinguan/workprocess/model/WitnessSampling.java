package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.Picture;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 见证取样
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WitnessSampling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //取样名称
    @Column
    private String samplingName;
    //取样部位
    @Column
    private String location;
    //创建人
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee creator;

    // 所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
    //数量
    @Column
    private Double quantity;
    //规格
    @Column
    private String standard;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    private Project project;
    //图像资料
    @OneToMany
    private List<Picture> pictureList;
    /**
     * 审核人审核
     */
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    private EmployeeAudit employeeAudit;
    /**
     * 总监审核
     *
     */
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    private EmployeeAudit majorAudit;
    @Column
    private String remark;
    // 判断保存还是提交 0是保存 1是提交
    @Column
    private Integer isSubmit;
    //职位等级 用来权限判断 等级最高为1
    @Column
    private Integer originRank;
    @Column
    private String processId;
    @Column
    @Version
    private int version;
    // 审批状态
    @Column
    private String auditStatus;
    @Transient
    private Boolean needAudit;
    @Transient
    private String taskId;

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


    public EmployeeAudit getMajorAudit() {
        return majorAudit;
    }

    public void setMajorAudit(EmployeeAudit majorAudit) {
        this.majorAudit = majorAudit;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSamplingName() {
        return samplingName;
    }

    public void setSamplingName(String samplingName) {
        this.samplingName = samplingName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
