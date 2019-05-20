package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.ExaminationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExaminationRecordRepository extends JpaRepository<ExaminationRecord, Long>, JpaSpecificationExecutor<ExaminationRecord> {
}
