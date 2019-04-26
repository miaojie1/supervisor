package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * 根据用户名和密码获取用户
     * @param username 用户名
     * @param password 密码（密文）
     * @return 匹配的用户
     */
    Employee findEmployeeByUsernameAndPassword(String username, String password);

    Employee findEmployeeByUsername(String username);

    /**
     * 获取当前登录用户
     * @return
     */
    Employee getCurrentUser();

    Employee saveEmployee(final Employee employee);

    List<Employee> listEmployeeByDepartment(Department department);
}
