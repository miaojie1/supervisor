package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zhangzhan
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentAudit {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    /**
     * 审核文件
     */
    @OneToOne
    private Document document;
    /**
     * 审核人员
     */
    @OneToMany
    private List<EmployeeAudit> employeeAuditList;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    /**
     * 发起人
     */
    @ManyToOne
    private Employee employee;
    /**
     * 截止日期
     */
    @Column
    private Date expirationDate;
    /**
     * 流程实例ID
     */
    @Column
    private String processId;
    @Version
    @Column
    private int version;
    @ManyToOne
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<EmployeeAudit> getEmployeeAuditList() {
        return employeeAuditList;
    }

    public void setEmployeeAuditList(List<EmployeeAudit> employeeAuditList) {
        this.employeeAuditList = employeeAuditList;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
