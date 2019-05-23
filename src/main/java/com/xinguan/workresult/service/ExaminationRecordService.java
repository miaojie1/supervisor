package com.xinguan.workresult.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.ExaminationRecord;
import com.xinguan.workresult.model.TestPaper;
import org.springframework.data.domain.Page;

public interface ExaminationRecordService {
    ExaminationRecord saveOrUpdate(TestPaper testPaper, Employee candidate);
    void update(String examinationRecordId, int score);
    ExaminationRecord findById(Long id);
    Page<ExaminationRecord> listExaminationRecordByPage(int pageSize,int pageNo,String testPaperId,String candidateId);
}
