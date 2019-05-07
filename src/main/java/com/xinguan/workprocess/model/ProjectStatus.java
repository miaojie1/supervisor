package com.xinguan.workprocess.model;

import javax.persistence.*;

/**
 * @author zhangzhan
 */
@Entity
public class ProjectStatus {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String remark;
    @Version
    @Column
    private int version;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
