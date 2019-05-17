package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TestPaperDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //题目名称
    @Column
    private String testPaperDetailName;
    //备注
    @Column
    private String remark;
    //题目满分
    @Column
    private Integer fullScore;

    @ManyToOne
    @JoinColumn(name = "test_paper_id")
    private TestPaper testPaper;

    @OneToMany
    private List<AnswerPaperDetail> answerPaperDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestPaperDetailName() {
        return testPaperDetailName;
    }

    public void setTestPaperDetailName(String testPaperDetailName) {
        this.testPaperDetailName = testPaperDetailName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFullScore() {
        return fullScore;
    }

    public void setFullScore(Integer fullScore) {
        this.fullScore = fullScore;
    }

    public TestPaper getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(TestPaper testPaper) {
        this.testPaper = testPaper;
    }

    public List<AnswerPaperDetail> getAnswerPaperDetails() {
        return answerPaperDetails;
    }

    public void setAnswerPaperDetails(List<AnswerPaperDetail> answerPaperDetails) {
        this.answerPaperDetails = answerPaperDetails;
    }
}
