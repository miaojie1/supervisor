package com.xinguan.workresult.service;

import com.xinguan.workresult.model.AccountRecord;
import org.springframework.data.domain.Page;



public interface AccountRecordService {
    /**
     * 分页获取监理台账
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AccountRecord> listAccountRecordByPage(int pageNo, int pageSize, String title);

    AccountRecord saveAccountRecord(AccountRecord accountRecord);
    void deleteAccountRecordById(Long id);

    /**
     * 根据记录id返回AccountRecord
     * @param id
     * @return
     */
    AccountRecord getAccByRecordId(Long id);

    /**
     * 根据id返回AccountRecord
     * @param id
     * @return
     */
    AccountRecord getAccById(Long id);
}
