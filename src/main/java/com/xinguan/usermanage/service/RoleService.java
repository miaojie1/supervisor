package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public interface RoleService {
//    查詢所有角色信息
    Page<Role> listRoleByPage(int pageSize, int pageNo, String roleName);
//    增加或修改角色信息
    Role addOrEditRole(Role role);
//    刪除角色信息
    Role getRoleById(Long id);
    void removeRole(Long id);
    void batchRemoveRole(Set<Long> ids);
}
