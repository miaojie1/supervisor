package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    List<Department> listAllDepartment();

    Page<Department> listDepartmentByPage(int pageSize, int pageNo, String departmentName, Employee employee);

    Department saveOrUpdate(Department department);

    Department getDepartmentById(Long id);

    void removeDepartmentBatch(String ids);
}
