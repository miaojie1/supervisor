package com.xinguan.usermanage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 附件
 *
 * @author zhangzhan
 * @date 2019-02-16 09:55
 */
@Entity
public class Attachment {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 附件名称
     */
    @Column
    private String name;
    /**
     * 附件路径
     */
    @Column
    private String url;
    /**
     * 上传日期
     */
    @Column
    private Date uploadDate;
    /**
     * 创建日期
     */
    @Column
    private Date createData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getCreateData() {
        return createData;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }
}