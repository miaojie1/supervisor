package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangzhan
 * @date 2019-03-25 14:11
 */
@Service
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

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

    @Override
    public List<Menu> listAllMenus() {
        return menuRepository.findAllByParentMenuIsNull();
    }

    @Override
    public Page<Menu> listMenuByPage(int pageSize, int pageNo, Map<String, Object> params) {
        Menu menu = new Menu();
        if (params != null) {
            transforObject(menu, params);
        }
        Example<Menu> example = getSimpleExample(menu);
        return menuRepository.findAll(example, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public Page<Menu> listMenuByPage(int pageSize, int pageNo, String name) {
        return menuRepository.findAll((Specification<Menu>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(name)) {
                //菜单名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }
    @Transactional
    @Override
    public Menu saveOrUpdate(Menu menu) {
        menu.setModificationDate(new Date());
        return menuRepository.saveAndFlush(menu);
    }

    @Transactional
    @Override
    public void removeMenu(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        menuRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void batchRemoveMenu(Set<Long> ids) {
        Set<Menu> menus = ids.stream().map(id -> getMenuById(id)).collect(Collectors.toSet());
        menuRepository.deleteInBatch(menus);
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).get();
    }

}
