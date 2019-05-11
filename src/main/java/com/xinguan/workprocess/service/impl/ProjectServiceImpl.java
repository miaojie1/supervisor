package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workprocess.service.ProjectService;
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

    @Override
    public List<Project> listAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    @Override
    public Project saveOrUpdate(Project project){
        Example<Project> projectExample = getSimpleExample(project);
        project.setModificationDate(new Date());
        if (!projectRepository.exists(projectExample)){
            project.setCreateDate(new Date());
        }
        return projectRepository.saveAndFlush(project);
    }

    @Transactional
    @Override
    public void removeProject(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        projectRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void removeProjectBatch(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            Set<Long> longIdList = Sets.newTreeSet(ids.split(",")).stream().map(Long::parseLong).collect(Collectors.toSet());
            longIdList.forEach(id -> projectRepository.deleteById(id));
        }
    }
}
