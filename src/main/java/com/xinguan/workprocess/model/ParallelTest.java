package com.xinguan.workprocess.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * @平行检验
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParallelTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //检查时间
    @Column
    private Date testData;

    // 部位
    @Column
    private String part;

    //简要内容
    @Column
    private String brief;

    //是否提交，0是保存，1是提交
    @Column
    private Integer isSubmit;

    //可修改的最低等级
    @Column
    private Integer originRank;

    //审核状态
    @ManyToOne
    private CheckStatus checkStatus;

    //修改人
    @ManyToOne
    private Employee sponsor;

    //项目
    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTestData() {
        return testData;
    }

    public void setTestData(Date testData) {
        this.testData = testData;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public Integer getOriginRank() {
        return originRank;
    }

    public void setOriginRank(Integer originRank) {
        this.originRank = originRank;
    }

    public CheckStatus getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Employee getSponsor() {
        return sponsor;
    }

    public void setSponsor(Employee sponsor) {
        this.sponsor = sponsor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
