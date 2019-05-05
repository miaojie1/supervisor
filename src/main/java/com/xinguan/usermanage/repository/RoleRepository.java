package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> , JpaSpecificationExecutor<Role> {

    List<Role> findRolesByOperationsIn(List<Long> operationsId);

    List<Role> findRolesByMenusIn(List<Long> menusId);

}
