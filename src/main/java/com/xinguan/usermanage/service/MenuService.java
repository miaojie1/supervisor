package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MenuService {
    Set<Menu> listMenuByEmployee(Employee employee);

    List<Menu> listAllMenus();
    Page<Menu> listMenuByPage(int pageSize, int pageNo, Map<String, Object> params);

    /**
     *
     * @param pageSize
     * @param pageNo
     * @param name 模糊查询
     * @return
     */
    Page<Menu> listMenuByPage(int pageSize, int pageNo, String name);
    Menu saveOrUpdate(Menu menu);

    void removeMenu(Long id);

    void batchRemoveMenu(Set<Long> ids);

    Menu getMenuById(Long id);
}
