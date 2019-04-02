package com.xinguan.usermanage.service.impl;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangzhan
 * @date 2019-03-25 14:11
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    /**
     * 获取当前登录用户的菜单
     *
     * @return
     */
    @Override
    public Set<Menu> listMenuByEmployee(Employee employee) {
        if (employee == null || employee.getRoles() == null || employee.getRoles().size() == 0) {
            return null;
        }
        //获取当前用户的所有角色
        Set<Role> roles = employee.getRoles();
        Set<Menu> menus = new HashSet<>();
        roles.forEach(e -> {
            if (e.getMenus() != null) {
                menus.addAll(e.getMenus());
            }
        });
        return menus.stream().filter(e -> e.getRootMenu() != null && e.getRootMenu()).collect(Collectors.toSet());

    }


}
