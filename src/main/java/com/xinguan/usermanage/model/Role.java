package com.xinguan.usermanage.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 角色
 *
 * @author zhangzhan
 * @date 2019-01-04 18:21
 */
@Entity
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @OneToOne(targetEntity = Role.class,cascade = CascadeType.ALL)
    private Role superiorRole;
    @ManyToMany(targetEntity = Menu.class,cascade = CascadeType.ALL)
    @JoinTable(name = "role_menu")
    private Set<Menu> menus;


    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Role getSuperiorRole() {
        return superiorRole;
    }

    public void setSuperiorRole(Role superiorRole) {
        this.superiorRole = superiorRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
