package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Set;

public interface MenuService {
    Set<Menu> listMenuByEmployee(Employee employee);

    Page<Menu> listMenuByPage(int pageSize, int pageNo, Map<String, Object> params);

    Menu addOrEditMenu(Menu menu);

    void removeMenu(Long id);

    void batchRemoveMenu(Set<Long> ids);

    Menu getMenuById(Long id);
}
