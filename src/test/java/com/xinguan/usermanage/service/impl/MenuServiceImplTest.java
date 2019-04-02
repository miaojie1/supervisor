package com.xinguan.usermanage.service.impl;

import com.alibaba.fastjson.JSON;
import com.xinguan.service.BaseServiceTest;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;

public class MenuServiceImplTest extends BaseServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImplTest.class);
    @Test(dataProvider = "employeename")
    public void testListMenuByCurrentUser(String employeename) {
        Employee employee = employeeService.findEmployeeByUsername(employeename);
        Set<Menu> menus = menuService.listMenuByEmployee(employee);
        if (menus != null) {
            logger.info(JSON.toJSON(menus).toString());

        }
    }

    @DataProvider(name = "employeename")
    private Object[] listEmployeeId() {
        return new Object[]{"", null, "admin", "none"," ","@!@#"};
    }

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
    }

}