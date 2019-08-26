package com.xinguan.workresult.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinguan.usermanage.model.DepartmentPosition;
import com.xinguan.usermanage.model.Employee;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 监理日志
 * @author MJ
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SupervisionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日期
     */
    @Column
    private Date date;
    /**
     * 发生人 对应监理工程师
     */
    @OneToOne
    private Employee supervisionEngineer;

    /**
     * 职位
     */
    @ManyToOne(targetEntity = DepartmentPosition.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private DepartmentPosition position;

    /**
     * 监理日志记录
     */
    @OneToMany(mappedBy = "supervisionLog",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<SupervisionLogRecord> supervisionLogRecords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getSupervisionEngineer() {
        return supervisionEngineer;
    }

    public void setSupervisionEngineer(Employee supervisionEngineer) {
        this.supervisionEngineer = supervisionEngineer;
    }

    public DepartmentPosition getPosition() {
        return position;
    }

    public void setPosition(DepartmentPosition position) {
        this.position = position;
    }

    public List<SupervisionLogRecord> getSupervisionLogRecords() {
        return supervisionLogRecords;
    }

    public void setSupervisionLogRecords(List<SupervisionLogRecord> supervisionLogRecords) {
        this.supervisionLogRecords = supervisionLogRecords;
    }
}
