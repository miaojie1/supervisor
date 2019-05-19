package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.TestPaperDetail;
import com.xinguan.workresult.service.TestPaperDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class TestPaperDetailServiceImpl extends BaseService<TestPaperDetail> implements TestPaperDetailService {
    @Override
    public Page<TestPaperDetail> listTestPaperDetailByTestPaperPage(int pageSize, int pageNo, String testPaperDetailName, String testPaperId){
        return testPaperDetailRepository.listTestPaperDetail(testPaperDetailName,testPaperId, PageRequest.of(pageNo,pageSize, Sort.Direction.ASC,"create_date"));
    }
    @Transactional
    @Override
    public TestPaperDetail saveOrUpdate(TestPaperDetail testPaperDetail){
        if(testPaperDetail.getId()==null){
            testPaperDetail.setCreateDate(new Date());
        }
        return testPaperDetailRepository.saveAndFlush(testPaperDetail);
    }
    @Transactional
    @Override
    public void deleteById (Long id){
        Assert.notNull(id, "The given Id must not be null!");
        testPaperDetailRepository.deleteById(id);
    }
}
