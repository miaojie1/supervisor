package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.ExaminationRecord;
import com.xinguan.workresult.model.TestPaper;
import com.xinguan.workresult.service.ExaminationRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExaminationRecordServiceImpl extends BaseService<ExaminationRecord> implements ExaminationRecordService {
    @Override
    public ExaminationRecord saveOrUpdate(TestPaper testPaper, Employee candidate){
        ExaminationRecord examinationRecord = new ExaminationRecord();
        examinationRecord.setCandidate(candidate);
        examinationRecord.setIsSubmit(0);
        examinationRecord.setTestDate(new Date());
        examinationRecord.setTestPaper(testPaper);
        return examinationRecordRepository.saveAndFlush(examinationRecord);
    }
}
