package com.xinguan.usermanage.controller;

import com.xinguan.service.BaseServiceTest;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MenuControllerTest extends BaseServiceTest {

    private static RestTemplate restTemplate = new RestTemplate();

    @BeforeMethod
    public void setUp() {

    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testAddOrEditMenu() {
        //ResponseEntity<Menu> responseEntity = restTemplate.getForEntity("http://localhost:8082/supervision/menu/addOrEditMenu/menuId/4", Menu.class);
        //System.out.println(responseEntity.getBody());


    }

    @Test
    public void testAddOrEdit() {
    }

}