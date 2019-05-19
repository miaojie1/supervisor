package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AnswerPaperDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //题目答案
    @Column
    private String answer;
    //获得的题目分数
    @Column
    private Integer gradeOfAnswer;

    //答题者
    @ManyToOne
    @JoinColumn(name = "respondent_id")
    private Employee respondent;

    @ManyToOne
    private TestPaperDetail testPaperDetail;
    @Column
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getGradeOfAnswer() {
        return gradeOfAnswer;
    }

    public void setGradeOfAnswer(Integer gradeOfAnswer) {
        this.gradeOfAnswer = gradeOfAnswer;
    }

    public Employee getRespondent() {
        return respondent;
    }

    public void setRespondent(Employee respondent) {
        this.respondent = respondent;
    }

    public TestPaperDetail getTestPaperDetail() {
        return testPaperDetail;
    }

    public void setTestPaperDetail(TestPaperDetail testPaperDetail) {
        this.testPaperDetail = testPaperDetail;
    }
}
