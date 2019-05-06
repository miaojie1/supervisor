package com.xinguan.usermanage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @OneToOne(targetEntity = Role.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Role superiorRole;
    @ManyToMany(targetEntity = Menu.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "role_menu")
    private Set<Menu> menus;
    @ManyToMany(targetEntity = Operation.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "role_operation")
    private Set<Operation> operations;
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Employee> employees;
    @Version
    @Column
    private int version;

    @JsonIgnore
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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
