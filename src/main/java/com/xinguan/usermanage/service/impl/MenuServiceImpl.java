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
        if (employee==null||employee.getRoles()==null||employee.getRoles().size()==0) {
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
        Set<Menu> result = new HashSet<>();
        processRootMenu(menus, result);
        Set<Menu> notRootMenu = menus.stream().filter(e -> null != e.getParentMenu()).collect(Collectors.toSet());
        result.forEach(e -> processSubMenu(notRootMenu, e));
        return result;

    }

    private void processSubMenu(Set<Menu> menus, Menu resultMenu) {
        Set<Menu> menuSet = menus.stream().filter(e -> e.getParentMenu().getId().equals(resultMenu.getId())).collect(Collectors.toSet());
        if (menuSet.size() == 0) {
            return;
        }
        menus.forEach(e -> {
            if (e.getParentMenu().getId().equals(resultMenu.getId())) {
                if (resultMenu.getSubMenus() == null) {
                    resultMenu.setSubMenus(new HashSet<>());
                }
                e.setParentMenu(null);
                resultMenu.getSubMenus().add(e);
            } else {
                processSubMenu(menus, e);
            }
        });
    }

    /**
     * 处理一级菜单
     *
     * @param menus  待处理的菜单
     * @param result 结果
     */
    private void processRootMenu(Set<Menu> menus, Set<Menu> result) {
        result.addAll(menus.stream().filter(e -> null == e.getParentMenu()).collect(Collectors.toSet()));
    }


}
