package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
