package com.xinguan.usermanage.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 系统资源
 *
 * @author zhangzhan
 * @date 2019-02-16 08:16
 */
@Entity
public class Menu {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String url;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @Column
    private boolean createAuthority;
    @Column
    private boolean modificationAuthority;
    @Column
    private boolean deleteAutority;
    @Column
    private boolean queryAutority;
    @ManyToOne
    private Employee createUser;
    @ManyToOne
    private Employee modificationUser;
    @OneToMany
    private List<Operation> operation;

    public List<Operation> getOperation() {
        return operation;
    }

    public void setOperation(List<Operation> operation) {
        this.operation = operation;
    }

    public boolean isCreateAuthority() {
        return createAuthority;
    }

    public void setCreateAuthority(boolean createAuthority) {
        this.createAuthority = createAuthority;
    }

    public boolean isModificationAuthority() {
        return modificationAuthority;
    }

    public void setModificationAuthority(boolean modificationAuthority) {
        this.modificationAuthority = modificationAuthority;
    }


    public boolean isDeleteAutority() {
        return deleteAutority;
    }

    public void setDeleteAutority(boolean deleteAutority) {
        this.deleteAutority = deleteAutority;
    }

    public boolean isQueryAutority() {
        return queryAutority;
    }

    public void setQueryAutority(boolean queryAutority) {
        this.queryAutority = queryAutority;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
