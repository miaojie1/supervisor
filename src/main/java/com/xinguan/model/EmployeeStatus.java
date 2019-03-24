package com.xinguan.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 员工状态
 *
 * @author zhangzhan
 * @date 2019-02-16 09:21
 */
@Entity
public class EmployeeStatus {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private boolean status;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @ManyToOne
    private Employee createUser;
    @ManyToOne
    private Employee modificationUser;

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
