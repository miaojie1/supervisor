package com.xinguan.usermanage.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author zhangzhan
 * @date 2019-03-23 14:02
 */
@Entity
public class Operation {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String buttonId;
    @Column
    private String buttonUrl;
    @Column
    private Date createTime;
    @Column
    private Date modificationTime;
    @Column
    private String name;

    @Version
    @Column
    private int version;
    @ManyToMany(mappedBy = "operations")
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }
}
