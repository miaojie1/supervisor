package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.service.DepartmentService;
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
import java.util.Optional;

/**
 * @author zhangzhan
 */
@Service
public class DepartmentServiceImpl extends BaseService<Department> implements DepartmentService {


    @Override
    public Page<Department> listDepartmentByPage(int pageSize, int pageNo, String departmentName, Employee employee) {
        Assert.notNull(employee, "employee must not be null");
        return departmentRepository.findAll((Specification<Department>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            Predicate idPredicate = null;
            Predicate parentIdPredicate = null;
            if (StringUtils.isNotBlank(departmentName)) {
                //部门名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + departmentName + "%");
            }
            //判断当前用户是否含有超级管理员角色
            Optional<Role> roleOptional = employee.getRoles().stream().filter(e -> "admin".equals(e.getName())).findAny();
            Long employeeDepartmentId = null;
            if (employee.getDepartment() != null) {
                employeeDepartmentId = employee.getDepartment().getId();
            }
            //如果当前用户不是超级管理员，只获取当前用户下所有的部门
            if (null != employeeDepartmentId && !roleOptional.isPresent()) {
                idPredicate = criteriaBuilder.equal(root.get("id").as(Long.class), employeeDepartmentId);
                parentIdPredicate = criteriaBuilder.equal(root.get("superiorDepartment").get("id").as(Long.class), employeeDepartmentId);
            } else if (null != employeeDepartmentId) {
                idPredicate = criteriaBuilder.isNotNull(root.get("id").as(Long.class));
            }

            Predicate preparePredicate = null;
            if (namePredicate != null && parentIdPredicate != null && idPredicate != null) {
                preparePredicate = criteriaBuilder.and(namePredicate, criteriaBuilder.or(idPredicate, parentIdPredicate));
            } else if (namePredicate != null && idPredicate != null) {
                preparePredicate = criteriaBuilder.and(namePredicate, idPredicate);
            } else if (idPredicate != null) {
                preparePredicate = criteriaBuilder.and(idPredicate);
            }
            if (preparePredicate != null) {
                criteriaQuery.where(preparePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }

    @Override
    @Transactional
    public Department addOrEditDepartment(Department department) {
        Example<Department> departmentExample = getSimpleExample(department);
        if (departmentRepository.exists(departmentExample)) {
            department.setModificationDate(new Date());
        } else {
            department.setCreateDate(new Date());
        }
        return departmentRepository.saveAndFlush(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.getOne(id);
    }

    @Transactional
    @Override
    public void removeDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
