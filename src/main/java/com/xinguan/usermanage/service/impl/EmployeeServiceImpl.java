package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
