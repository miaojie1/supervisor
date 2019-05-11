package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangzhan
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 项目名称
     */
    @Column
    private String name;
    /**
     * 项目编号
     */
    @Column
    private String no;
    /**
     * 标段
     */
    @Column
    private String section;
    /**
     * 项目状态
     */
    @ManyToOne
    private ProjectStatus projectStatus;
    /**
     * 施工单位
     */
    @Column
    private String construction;

    /**
     * 建设单位
     */
    @Column
    private String development;

    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    /**
     * 项目经理
     */
    @ManyToOne
    private Employee manager;
    @Version
    @Column
    private int version;
    @OneToOne(mappedBy = "project")
    @JsonIgnore
    private ProjectSupervisionDepartment projectSupervisionDepartment;

    public ProjectSupervisionDepartment getProjectSupervisionDepartment() {
        return projectSupervisionDepartment;
    }

    public void setProjectSupervisionDepartment(ProjectSupervisionDepartment projectSupervisionDepartment) {
        this.projectSupervisionDepartment = projectSupervisionDepartment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getDevelopment() {
        return development;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }
}
