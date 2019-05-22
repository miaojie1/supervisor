package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.AnswerPaperDetail;
import com.xinguan.workresult.model.TestPaperDetail;
import com.xinguan.workresult.service.AnswerPaperDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class AnswerPaperDetailServiceImpl extends BaseService<AnswerPaperDetail> implements AnswerPaperDetailService {

    @Override
    public Page<AnswerPaperDetail> listAnswerPaperDetailByTestPaperDetailPage(int pageSize, int pageNo, String testPaperDetailId, String respondentId){
        return answerPaperDetailRepository.listAnswerPaperDetail(testPaperDetailId,respondentId, PageRequest.of(pageNo,pageSize, Sort.Direction.ASC,"create_date"));
    }
    @Transactional
    @Override
    public AnswerPaperDetail saveOrUpdate(AnswerPaperDetail answerPaperDetail){
        if(answerPaperDetail.getId()==null){
            answerPaperDetail.setCreateDate(new Date());
        }
        return answerPaperDetailRepository.saveAndFlush(answerPaperDetail);
    }
    @Override
    public AnswerPaperDetail getAnswerDetailByDetailAndRespondent(Employee respondent, TestPaperDetail testPaperDetail){
        return answerPaperDetailRepository.findByTestPaperDetailAndRespondent(testPaperDetail.getId(),respondent.getId());
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        answerPaperDetailRepository.deleteById(id);
    }
}
