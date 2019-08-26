package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.CheckAcceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CheckAcceptanceRepository extends JpaRepository<CheckAcceptance, Long>, JpaSpecificationExecutor<CheckAcceptance> {
}
