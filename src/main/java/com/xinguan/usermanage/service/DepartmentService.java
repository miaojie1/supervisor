package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Page<Department> listDepartmentByPage(int pageSize, int pageNo, String departmentName, Employee employee);

    Department saveOrUpdate(Department department);

    Department getDepartmentById(Long id);

    void removeDepartment(Long id);

}
