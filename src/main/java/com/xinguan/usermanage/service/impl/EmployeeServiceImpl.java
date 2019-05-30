package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.DepartmentPosition;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.EmployeeStatus;
import com.xinguan.usermanage.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.List;

/**
 * @author zhangzhan
 * @date 2019-02-16 15:38
 */
@Service
public class EmployeeServiceImpl extends BaseService<Employee> implements EmployeeService {

    @Override
    public Employee findEmployeeByUsernameAndPassword(final String username, final String password) {
        return employeeRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee getCurrentUser() {
        Employee currentEmployee = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            currentEmployee = findEmployeeByUsername(username);
        }
        return currentEmployee;
    }

    @Transactional
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> listEmployeeByDepartment(Department department) {
        return employeeRepository.findAllByDepartment(department);
    }

    @Override
    public Page<Employee> listEmployeeByPage(int pageSize, int pageNo, String username) {
        Employee employee = new Employee();
        Example<Employee> example;

        if (username != null) {
            //transforObject(employee, params);
            employee.setUsername("%" + username + "%");
            //example = Example.of(employee);
        }
        example = getSimpleExample(employee);
        return employeeRepository.findAll(example, PageRequest.of(pageNo, pageSize));

    }

    @Override
    public List<Employee> listAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    @Override
    public Employee saveOrUpdate(Employee employee) {
        Example<Employee> employeeExample = getSimpleExample(employee);
        if (employee.getId()!=null) {
            employee.setModificationDate(new Date());
        } else {
            employee.setCreateDate(new Date());
        }
        return employeeRepository.saveAndFlush(employee);
    }

    @Transactional
    @Override
    public void removeEmployee(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        employeeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void batchRemoveEmployee(Set<Long> ids) {
        Set<Employee> employees = ids.stream().map(id -> getEmployeeById(id)).collect(Collectors.toSet());
        employeeRepository.deleteInBatch(employees);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.getOne(id);
    }

}
