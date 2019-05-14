package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.SupervisionLogRecord;
import com.xinguan.workresult.service.SuperviLogRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SuperviLogRecordServiceImpl extends BaseService<SupervisionLogRecord> implements SuperviLogRecordService {

    @Override
    public Page<SupervisionLogRecord> listSupervisionLogRecordByPage(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id");
        return supervisionLogRecordRepository.findAll(pageable);
    }

    @Override
    public void deleteSupLogRecordById(Long id){
        supervisionLogRecordRepository.deleteById(id);
    }
}
