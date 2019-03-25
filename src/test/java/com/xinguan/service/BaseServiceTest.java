package com.xinguan.service;

import com.xinguan.Application;
import com.xinguan.usermanage.service.EmployeeService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zhangzhan
 * @date 2019-03-24 21:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BaseServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    protected EmployeeService employeeService;

}
