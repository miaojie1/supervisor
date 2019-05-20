package com.xinguan.workresult.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.ExaminationRecord;
import com.xinguan.workresult.model.TestPaper;

public interface ExaminationRecordService {
    ExaminationRecord saveOrUpdate(TestPaper testPaper, Employee candidate);
}
