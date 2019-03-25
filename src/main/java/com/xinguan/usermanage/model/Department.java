package com.xinguan.usermanage.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 部门
 *
 * @author zhangzhan
 * @date 2019-02-16 08:20
 */
@Entity
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
     * 部门主管
     */
    @ManyToOne
    private Employee chiefEmployee;
    /**
     * 上级部门
     */
    @ManyToOne
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
    @ManyToOne
    private Employee createUser;
    @ManyToOne
    private Employee modificationUser;

    public Employee getChiefEmployee() {
        return chiefEmployee;
    }

    public void setChiefEmployee(Employee chiefEmployee) {
        this.chiefEmployee = chiefEmployee;
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


    public Employee getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Employee createUser) {
        this.createUser = createUser;
    }

    public Employee getModificationUser() {
        return modificationUser;
    }

    public void setModificationUser(Employee modificationUser) {
        this.modificationUser = modificationUser;
    }
}
