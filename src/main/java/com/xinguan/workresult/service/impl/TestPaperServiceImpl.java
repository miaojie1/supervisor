package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.TestPaper;
import com.xinguan.workresult.service.TestPaperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class TestPaperServiceImpl extends BaseService<TestPaper> implements TestPaperService {
    @Override
    public Page<TestPaper> listTestPaperByPage(int pageSize, int pageNo, String departmentId, String testPaperName, String testPaperCategoryId){
        return testPaperRepository.listTestPaper(testPaperName,departmentId,testPaperCategoryId, PageRequest.of(pageNo,pageSize, Sort.Direction.ASC,"create_date"));
    }
    @Transactional
    @Override
    public TestPaper saveOrUpdate(TestPaper testPaper){
        if(testPaper.getId()==null){
            testPaper.setCreateDate(new Date());
            testPaper.setFullScore(0);
        }
        return testPaperRepository.saveAndFlush(testPaper);
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        testPaperRepository.deleteById(id);
    }
    @Override
    public TestPaper getById(Long id){
        return testPaperRepository.findById(id).get();
    }
}
