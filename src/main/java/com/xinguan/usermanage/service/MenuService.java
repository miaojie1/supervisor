package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Menu;

import java.util.Set;

public interface MenuService {
    Set<Menu> listMenuByCurrentUser();
}
