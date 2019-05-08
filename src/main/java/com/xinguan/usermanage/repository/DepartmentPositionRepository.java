package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.DepartmentPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DepartmentPositionRepository extends JpaRepository<DepartmentPosition, Long> {
    @Query(value = "select * from department_position  where department_id in ?1", nativeQuery = true)
    List<DepartmentPosition> finAllByDepartment(Set<Long> departmentIds);
}
