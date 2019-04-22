package com.xinguan.usermanage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Column
    private String url;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Operation> operation;
    @Column
    private Integer sort;
    @Column
    private Boolean status;
    @Column
    private String remark;
    @OneToMany(mappedBy = "parentMenu")
    private Set<Menu> subMenus;
    @Column
    private Boolean rootMenu;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Menu parentMenu;
    @Transient
    private Long parentMenuId;
    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles;
    @Version
    @Column
    private int version;

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public int getVersion() {
        return version;
    }

    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    @JsonIgnore
    public Set<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(Set<Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getRootMenu() {
        return rootMenu;
    }

    public void setRootMenu(Boolean rootMenu) {
        this.rootMenu = rootMenu;
    }

    public List<Operation> getOperation() {
        return operation;
    }

    public void setOperation(List<Operation> operation) {
        this.operation = operation;
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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


}
