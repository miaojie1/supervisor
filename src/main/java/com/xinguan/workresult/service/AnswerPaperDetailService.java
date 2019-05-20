package com.xinguan.workresult.service;

import com.xinguan.workresult.model.AnswerPaperDetail;
import org.springframework.data.domain.Page;

public interface AnswerPaperDetailService {
    Page<AnswerPaperDetail> listAnswerPaperDetailByTestPaperDetailPage(int pageSize, int pageNo, String testPaperDetailId, String respondentId);
    AnswerPaperDetail saveOrUpdate(AnswerPaperDetail answerPaperDetail);
    void deleteById (Long id);
}
