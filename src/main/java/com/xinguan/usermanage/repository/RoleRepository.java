package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findRolesByOperationsIn(List<Long> operationsId);

    List<Role> findRolesByMenusIn(List<Long> menusId);
}
