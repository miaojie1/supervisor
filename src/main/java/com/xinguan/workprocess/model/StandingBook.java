package com.xinguan.workprocess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 进场验收台账记录
 * 暂时不用
 *
 * @author zhangzhan
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StandingBook {

    @Id
    @GeneratedValue
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
