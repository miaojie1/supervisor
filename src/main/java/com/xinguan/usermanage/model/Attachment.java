package com.xinguan.usermanage.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 附件
 *
 * @author zhangzhan
 * @date 2019-02-16 09:55
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Version
    @Column
    private int version;

    @ManyToMany(mappedBy = "attachments")
    private List<PostingSystem> postingSystemList;

    @JsonIgnore
    public List<PostingSystem> getPostingSystemList() {
        return postingSystemList;
    }

    public void setPostingSystemList(List<PostingSystem> postingSystemList) {
        this.postingSystemList = postingSystemList;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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

}
