package com.xinguan.usermanage.service.impl;

import com.xinguan.service.BaseServiceTest;
import com.xinguan.usermanage.model.Menu;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertNull;

public class MenuServiceImplTest extends BaseServiceTest {

    @Test
    public void testListMenuByCurrentUser() {
        Set<Menu> menus = menuService.listMenuByCurrentUser();
        assertNull(menus,null);
    }
}