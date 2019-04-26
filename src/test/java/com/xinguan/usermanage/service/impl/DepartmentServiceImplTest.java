package com.xinguan.usermanage.service.impl;

import com.xinguan.service.BaseServiceTest;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DepartmentServiceImplTest extends BaseServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImplTest.class);

    private Department department = null;
    private static Long departmentId = 0L;

    @BeforeMethod
    public void setUp() {
        department = new Department();
        department.setName("test");
        department.setDescription("test");
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testListDepartmentByPage() {
        Employee employee = employeeService.findEmployeeByUsername("admin");
        Page<Department> departmentPage = departmentService.listDepartmentByPage(10, 0, "人力", employee);
        LOGGER.info("total page:" + departmentPage.getTotalPages() + ",total elements:" + departmentPage.getTotalElements() + ",current page:" + departmentPage.getNumber());
        LOGGER.info(departmentPage.getContent().toString());
    }

    @Test(dependsOnMethods = "testListDepartmentByPage")
    public void testSaveOrUpdate() {
        department = departmentService.saveOrUpdate(department, 1L);
        Assert.assertNotNull(department);
        departmentId = department.getId();
    }

    @Test(dependsOnMethods = "testSaveOrUpdate")
    public void testGetDepartmentById() {
        Department getDepartment = departmentService.getDepartmentById(departmentId);
        Assert.assertEquals(getDepartment.getName(), "test");
    }

    @Test(dependsOnMethods = "testRemoveDepartment")
    public void testRemoveDepartmentBatch() {
    }
}