package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
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
    //试题内容
    @Column
    private String content;
    //题目满分
    @Column
    private Integer fullScore;

    @ManyToOne
    @JoinColumn(name = "test_paper_id")
    private TestPaper testPaper;

    @Column
    private Date createDate;
    @OneToMany
    private List<AnswerPaperDetail> answerPaperDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
