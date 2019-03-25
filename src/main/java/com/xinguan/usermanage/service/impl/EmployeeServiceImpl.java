package com.xinguan.usermanage.service.impl;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.repository.EmployeeRepository;
import com.xinguan.usermanage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author zhangzhan
 * @date 2019-02-16 15:38
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;

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

}
