package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExaminationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //考试时间
    @Column
    private Date testDate;
    //参考人
    @OneToOne
    private Employee candidate;
    //考卷
    @OneToOne
    private TestPaper testPaper;
    //是否交卷 0没交卷 1已交卷
    @Column
    private Integer isSubmit;
    //分数
    @Column
    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Employee getCandidate() {
        return candidate;
    }

    public void setCandidate(Employee candidate) {
        this.candidate = candidate;
    }

    public TestPaper getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(TestPaper testPaper) {
        this.testPaper = testPaper;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
