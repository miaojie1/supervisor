package com.xinguan.service.impl;

import com.xinguan.service.BaseServiceTest;
import com.xinguan.usermanage.model.Employee;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNull;


public class EmployeeServiceImplTest extends BaseServiceTest {


    @BeforeMethod
    public void setUp() {

    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testFindEmployeeByUsernameAndPassword() {

    }


    @Test(dataProvider = "usernameParam")
    public void testFindEmployeeByUsername(String username) {
        Employee employee = employeeService.findEmployeeByUsername(username);
        assertNull(employee);
    }

    @Test
    public void testGetCurrentUser() {
    }

    @DataProvider(name = "usernameParam")
    private Object[] getParamters() {
        return new String[]{null, ""};
    }
}