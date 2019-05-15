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


}
