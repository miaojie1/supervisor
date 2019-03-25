package com.xinguan.usermanage.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 系统公告
 *
 * @author zhangzhan
 * @date 2019-02-16 09:52
 */
@Entity
public class PostingSystem {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String content;
    @ManyToMany
    @JoinTable(name = "postingSystem_attachment")
    private List<Attachment> attachments;
    @Column
    private Date effectDate;
    @Column
    private Date expireDate;
    @Column
    private Date createDate;
    @Column
    private Date modificationDate;
    @ManyToOne
    private Employee createUser;
    @ManyToOne
    private Employee modificationUser;


    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Employee getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Employee createUser) {
        this.createUser = createUser;
    }

    public Employee getModificationUser() {
        return modificationUser;
    }

    public void setModificationUser(Employee modificationUser) {
        this.modificationUser = modificationUser;
    }
}
