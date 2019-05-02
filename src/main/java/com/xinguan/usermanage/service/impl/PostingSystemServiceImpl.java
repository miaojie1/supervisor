package com.xinguan.usermanage.service.impl;


import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Attachment;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.PostingSystem;
import com.xinguan.usermanage.service.PostingSystemService;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostingSystemServiceImpl extends BaseService<PostingSystem> implements PostingSystemService {

    @Override
    public PostingSystem getPostingSystemById(Long id) {
        return postingSystemRepository.getOne(id);
    }

    @Override
    public Page<PostingSystem> listExpPostingByPage(int pageSize, int pageNo) {
        return postingSystemRepository.findAll((Specification<PostingSystem>) (root, criteriaQuery, criteriaBuilder) -> {
            Date nowDate = new Date();
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.greaterThan(root.<Date> get("expireDate"),nowDate),criteriaBuilder.lessThan(root.<Date> get("effectDate"),nowDate)));
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }

    @Override
    public Page<PostingSystem> listPostingByPage(int pageSize, int pageNo, String postingName) {
        return postingSystemRepository.findAll((Specification<PostingSystem>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(postingName)) {
                //公告名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + postingName + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }

    @Transactional
    @Override
    public PostingSystem saveOrUpdate(PostingSystem postingSystem, Employee announcer,Long attachmentId){
        Example<PostingSystem> postingSystemExample = getSimpleExample(postingSystem);
        if (postingSystemRepository.exists(postingSystemExample))
            postingSystem.setModificationDate(new Date());
        else {
            postingSystem.setCreateDate(new Date());
            postingSystem.setAnnouncer(announcer);
        }
        if (attachmentId!=null) {
            List<Attachment> attachments = postingSystem.getAttachments();
            attachments.add(attachmentRepository.getOne(attachmentId));
            postingSystem.setAttachments(attachments);
        }
        return postingSystemRepository.saveAndFlush(postingSystem);
    }

    @Transactional
    @Override
    public void removePosting(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        postingSystemRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void removePostingBatch(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            Set<Long> longIdList = Sets.newTreeSet(ids.split(",")).stream().map(Long::parseLong).collect(Collectors.toSet());
            longIdList.forEach(id -> {
                PostingSystem postingSystem = postingSystemRepository.getOne(id);
                List<Attachment> attachments = postingSystem.getAttachments();
                attachments.forEach(attachment -> {
                    attachment.setPostingSystemList(null);
                    attachmentRepository.saveAndFlush(attachment);
                });
                postingSystemRepository.saveAndFlush(postingSystem);
                postingSystemRepository.deleteById(id);
            });
        }
    }
}
