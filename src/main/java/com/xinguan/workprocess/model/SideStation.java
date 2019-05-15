package com.xinguan.workprocess.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.Picture;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 旁站
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SideStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //发起人
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee sponsor;

    //修改人
    @ManyToMany
    @JoinColumn(name="employee_id")
    private Employee rectifier;

    // 所属部门
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    //创建时间
    @Column
    private Date createDate;
    //修改时间
    @Column
    private Date modificationDate;

    //旁站时间
    @Column
    private Date sideStationDate;

    //旁站照片
    @OneToMany
    private List<Picture> pictureList;

    //所属项目
    @OneToMany
    private Project project;

    // 简要内容
    @Column
    private String brief;
    //处理问题
    @Column
    private String problems;
}
