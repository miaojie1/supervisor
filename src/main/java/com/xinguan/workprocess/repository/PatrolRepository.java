package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.Patrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatrolRepository extends JpaRepository<Patrol, Long>, JpaSpecificationExecutor<Patrol> {
}
