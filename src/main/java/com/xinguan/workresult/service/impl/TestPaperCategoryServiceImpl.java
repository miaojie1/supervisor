package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.TestPaperCategory;
import com.xinguan.workresult.service.TestPaperCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPaperCategoryServiceImpl extends BaseService<TestPaperCategory> implements TestPaperCategoryService {
    @Override
    public List<TestPaperCategory> listAllTestPaperCategories(){
        return testPaperCategoryRepository.findAll();
    }
}
