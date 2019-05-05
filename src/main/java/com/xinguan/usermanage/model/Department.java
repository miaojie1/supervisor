package com.xinguan.usermanage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 部门
 *
 * @author zhangzhan
 * @date 2019-02-16 08:20
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Department {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 部门名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 部门描述
     */
    @Column
    private String description;

    /**
     * 上级部门
     */
    @OneToOne(targetEntity = Department.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Department superiorDepartment;
    /**
     * 创建日期
     */
    @Column
    private Date createDate;
    /**
     * 修改日期
     */
    @Column
    private Date modificationDate;

    @Version
    @Column
    private int version;

    /**
     * 岗位
     */
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<DepartmentPosition> departmentPositions;

    public List<DepartmentPosition> getDepartmentPositions() {
        return departmentPositions;
    }

    public void setDepartmentPositions(List<DepartmentPosition> departmentPositions) {
        this.departmentPositions = departmentPositions;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Department getSuperiorDepartment() {
        return superiorDepartment;
    }

    public void setSuperiorDepartment(Department superiorDepartment) {
        this.superiorDepartment = superiorDepartment;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


}
