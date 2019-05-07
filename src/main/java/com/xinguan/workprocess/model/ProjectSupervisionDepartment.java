package com.xinguan.workprocess.model;

import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zhangzhan
 */
@Entity
public class ProjectSupervisionDepartment {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    /**
     * 总监理工程师
     */
    @ManyToOne
    private Employee major;
    /**
     * 专业监理工程师
     */
    @ManyToMany
    private List<Employee> membership;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @Version
    @Column
    private int version;
    @Column
    private String remark;
    @OneToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getMajor() {
        return major;
    }

    public void setMajor(Employee major) {
        this.major = major;
    }

    public List<Employee> getMembership() {
        return membership;
    }

    public void setMembership(List<Employee> membership) {
        this.membership = membership;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
