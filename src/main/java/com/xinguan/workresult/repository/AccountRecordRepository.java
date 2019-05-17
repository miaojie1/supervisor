package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.AccountRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRecordRepository extends JpaRepository<AccountRecord, Long>, JpaSpecificationExecutor<AccountRecord> {
    AccountRecord findAccountRecordByRecordId(Long id);
}
