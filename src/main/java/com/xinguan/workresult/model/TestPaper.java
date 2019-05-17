package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TestPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //试卷名称
    @Column
    private String testPaperName;
    //备注
    @Column
    private String remark;
    //所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    //满分
    @Column
    private Integer fullScore;

    //试卷类别
    @ManyToOne
    private TestPaperCategory testPaperCategory;

    @OneToMany
    private List<TestPaperDetail> testPaperDetails;

    @Version
    @Column
    private int version;

    public List<TestPaperDetail> getTestPaperDetails() {
        return testPaperDetails;
    }

    public void setTestPaperDetails(List<TestPaperDetail> testPaperDetails) {
        this.testPaperDetails = testPaperDetails;
    }

    public Integer getFullScore() {
        return fullScore;
    }

    public void setFullScore(Integer fullScore) {
        this.fullScore = fullScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestPaperName() {
        return testPaperName;
    }

    public void setTestPaperName(String testPaperName) {
        this.testPaperName = testPaperName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public TestPaperCategory getTestPaperCategory() {
        return testPaperCategory;
    }

    public void setTestPaperCategory(TestPaperCategory testPaperCategory) {
        this.testPaperCategory = testPaperCategory;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
