package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 在线会签 暂时不做
 * @author MJ
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OnlineSign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 会签名称
    @Column
    private String signName;
    //发起人
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee sponsor;
    // 所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @Column
    private String remark;
    // 当前职位等级
    @Column
    private Integer originRank;
    @Column
    private String processId;
    @Column
    @Version
    private int version;
    // 所属项目
    @ManyToOne
    private Project project;
    // 审核情况
    @OneToMany
    private List<EmployeeAudit> employeeAuditList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Employee getSponsor() {
        return sponsor;
    }

    public void setSponsor(Employee sponsor) {
        this.sponsor = sponsor;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<EmployeeAudit> getEmployeeAuditList() {
        return employeeAuditList;
    }

    public void setEmployeeAuditList(List<EmployeeAudit> employeeAuditList) {
        this.employeeAuditList = employeeAuditList;
    }
}
