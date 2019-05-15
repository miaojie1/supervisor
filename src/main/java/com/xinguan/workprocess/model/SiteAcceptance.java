package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.Picture;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zhangzhan
 * 进场验收
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SiteAcceptance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String materialName;

    //发起人
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee sponsor;

    // 所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
    @Column
    private Double quantity;
    @Column
    private String standard;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @OneToMany
    private List<Picture> pictureList;
    @OneToOne
    private EmployeeAudit employeeAudit;
    @Column
    private String remark;
    @Column
    private Integer originRank;
    @Column
    private String processId;
    @Column
    @Version
    private int version;
    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOriginRank() {
        return originRank;
    }

    public void setOriginRank(Integer originRank) {
        this.originRank = originRank;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
