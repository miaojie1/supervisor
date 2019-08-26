package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.SupervisionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupervisionLogRepository extends JpaRepository<SupervisionLog, Long>, JpaSpecificationExecutor<SupervisionLog> {
}
