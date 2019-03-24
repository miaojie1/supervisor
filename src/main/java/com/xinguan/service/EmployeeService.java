package com.xinguan.service;

import com.xinguan.model.Employee;

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
}
