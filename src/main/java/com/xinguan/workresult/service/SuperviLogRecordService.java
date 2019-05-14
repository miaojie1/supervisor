package com.xinguan.workresult.service;

import com.xinguan.workresult.model.SupervisionLogRecord;
import org.springframework.data.domain.Page;

public interface SuperviLogRecordService {
    /**
     * 分页获取监理日志记录
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<SupervisionLogRecord> listSupervisionLogRecordByPage(int pageNo, int pageSize);
    void deleteSupLogRecordById(Long id);
}
