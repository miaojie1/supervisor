package com.xinguan.workresult.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.AnswerPaperDetail;
import com.xinguan.workresult.model.TestPaperDetail;
import org.springframework.data.domain.Page;

public interface AnswerPaperDetailService {
    Page<AnswerPaperDetail> listAnswerPaperDetailByTestPaperDetailPage(int pageSize, int pageNo, String testPaperDetailId, String respondentId);
    AnswerPaperDetail saveOrUpdate(AnswerPaperDetail answerPaperDetail);
    AnswerPaperDetail getAnswerDetailByDetailAndRespondent(Employee respondent, TestPaperDetail testPaperDetail);
    void deleteById (Long id);
}
