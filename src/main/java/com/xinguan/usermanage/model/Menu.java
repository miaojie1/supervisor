package com.xinguan.usermanage.model;

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

    @OneToMany(targetEntity = Operation.class,cascade =CascadeType.ALL)
    private List<Operation> operation;
    @Column
    private Integer sort;
    @OneToOne(targetEntity = Menu.class,cascade = CascadeType.ALL)
    private Menu parentMenu;
    @Column
    private boolean status;
    @Column
    private String remark;
    @Transient
    private Set<Menu> subMenus;

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
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
