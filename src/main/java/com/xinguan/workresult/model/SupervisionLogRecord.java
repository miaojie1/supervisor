package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.workprocess.model.Project;

import javax.persistence.*;
import java.util.Date;

/**
 * @author MJ
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SupervisionLogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 记录发生时间
     */
    @Column
    private Date recordDate;

    /**
     * 对应项目
     */
    @ManyToOne(targetEntity = Project.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Project project;

    /**
     * 记录内容
     */
    @Column
    private String recordContent;
}
