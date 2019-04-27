package com.xinguan.usermanage.service.impl;


import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.PostingSystem;
import com.xinguan.usermanage.service.PostingSystemService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostingSystemServiceImpl extends BaseService<PostingSystem> implements PostingSystemService {

    @Override
    public PostingSystem getPostingSystemById(Long id) {
        return postingSystemRepository.getOne(id);
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
    public PostingSystem addOrEditPosting(PostingSystem postingSystem){
        Example<PostingSystem> postingSystemExample = getSimpleExample(postingSystem);
        if (postingSystemRepository.exists(postingSystemExample))
            postingSystem.setModificationDate(new Date());
        else
            postingSystem.setCreateDate(new Date());
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
    public void batchRemovePosting(Set<Long> ids) {
        Set<PostingSystem> postingSystems = ids.stream().map(id -> getPostingSystemById(id)).collect(Collectors.toSet());
        postingSystemRepository.deleteInBatch(postingSystems);
    }
}