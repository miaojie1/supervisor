package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.SupervisionLogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupervisionLogRecordRepository extends JpaRepository<SupervisionLogRecord, Long>, JpaSpecificationExecutor<SupervisionLogRecord> {
}
