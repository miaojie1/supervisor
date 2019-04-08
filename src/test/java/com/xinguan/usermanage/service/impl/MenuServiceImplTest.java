package com.xinguan.usermanage.service.impl;

import com.alibaba.fastjson.JSON;
import com.xinguan.service.BaseServiceTest;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
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

    @Test
    public void testListMenuByPage() {
        Map<String, Object> param = new HashMap<>();
        //param.put("name", "功能");
        Page<Menu> menus = menuService.listMenuByPage(2, 1, param);
        System.out.println(menus);
        System.out.println("总条数：" + menus.getTotalElements() + ", 总页数：" + menus.getTotalPages() + ",当前页：" + menus.getNumber() + ",当前页条数：" + menus.getNumberOfElements());
        System.out.println(menus.getContent());
    }
}