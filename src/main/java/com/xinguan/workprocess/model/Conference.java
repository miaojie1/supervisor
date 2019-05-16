package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zhangzhan
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conference {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private String content;
    /**
     * 发起人
     */
    @ManyToOne
    private Employee initiator;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @Column
    private String location;
    @Column
    private String remark;
    @OneToMany(mappedBy = "conference", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<ConferenceSummary> conferenceSummaryList;
    // 所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
    @Column
    private Integer originRank;
    @Column
    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Employee getInitiator() {
        return initiator;
    }

    public void setInitiator(Employee initiator) {
        this.initiator = initiator;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ConferenceSummary> getConferenceSummaryList() {
        return conferenceSummaryList;
    }

    public void setConferenceSummaryList(List<ConferenceSummary> conferenceSummaryList) {
        this.conferenceSummaryList = conferenceSummaryList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getOriginRank() {
        return originRank;
    }

    public void setOriginRank(Integer originRank) {
        this.originRank = originRank;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
