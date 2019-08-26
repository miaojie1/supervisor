package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.DepartmentPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePositionRepository extends JpaRepository<DepartmentPosition, Long> {

}
