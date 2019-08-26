package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudyMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //学习资料名称
    @Column
    private String studyMaterialName;
    //备注
    @Column
    private String remark;
    //所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    //学习资料附件名称
    @Column
    private String studyMaterialAttachmentName;

    //学习资料附件地址
    @Column
    private String studyMaterialAttachmentUrl;

    //学习资料类别
    @ManyToOne
    private StudyMaterialCategory studyMaterialCategory;

    @Column
    private Date createDate;

    @Version
    @Column
    private int version;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudyMaterialName() {
        return studyMaterialName;
    }

    public void setStudyMaterialName(String studyMaterialName) {
        this.studyMaterialName = studyMaterialName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getStudyMaterialAttachmentName() {
        return studyMaterialAttachmentName;
    }

    public void setStudyMaterialAttachmentName(String studyMaterialAttachmentName) {
        this.studyMaterialAttachmentName = studyMaterialAttachmentName;
    }

    public String getStudyMaterialAttachmentUrl() {
        return studyMaterialAttachmentUrl;
    }

    public void setStudyMaterialAttachmentUrl(String studyMaterialAttachmentUrl) {
        this.studyMaterialAttachmentUrl = studyMaterialAttachmentUrl;
    }

    public StudyMaterialCategory getStudyMaterialCategory() {
        return studyMaterialCategory;
    }

    public void setStudyMaterialCategory(StudyMaterialCategory studyMaterialCategory) {
        this.studyMaterialCategory = studyMaterialCategory;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
