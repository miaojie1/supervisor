package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.ProjectSupervisionDepartment;
import com.xinguan.workprocess.service.ProjectSupervisionDepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class ProjectSupervisionDepartmentServiceImpl extends BaseService<ProjectSupervisionDepartment> implements ProjectSupervisionDepartmentService {
    @Override
    public List<ProjectSupervisionDepartment> listAllProSuperDeparts(){
        return projectSupervisionDepartmentRepository.findAll();
    }

    @Override
    public Page<ProjectSupervisionDepartment> listProjectSupervisionDbByPage(int pageSize, int pageNo, String projectSupervisionDpName) {
        System.out.println("走到这了1");
        return projectSupervisionDepartmentRepository.findAll((Specification<ProjectSupervisionDepartment>) (root, criteriaQuery, criteriaBuilder) -> {
            System.out.println("走到这了2");
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(projectSupervisionDpName)) {
                //监理部名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + projectSupervisionDpName + "%");
                criteriaQuery.where(namePredicate);
            }
            System.out.println("走到这了3");
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }

    @Override
    public ProjectSupervisionDepartment saveOrUpdate(ProjectSupervisionDepartment projectSupervisionDepartment) {
        return projectSupervisionDepartmentRepository.save(projectSupervisionDepartment);
    }

    @Override
    public void removeProjectSupervisionDepartment(Long id) {
        projectSupervisionDepartmentRepository.deleteById(id);
    }

    @Override
    public void removeProjectSupervisionDepartmentBatch(String ids) {

    }
}
