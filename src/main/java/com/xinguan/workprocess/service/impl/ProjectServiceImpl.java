package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workprocess.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
public class ProjectServiceImpl extends BaseService<Project> implements ProjectService {
    @Override
    public Page<Project> listProjectByPage(int pageSize, int pageNo, String projectName) {
        return projectRepository.findAll((Specification<Project>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(projectName)) {
                //项目名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + projectName + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }
}
