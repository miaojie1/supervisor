package com.xinguan.workresult.service;

import com.xinguan.workresult.model.TestPaper;
import org.springframework.data.domain.Page;

public interface TestPaperService {
    Page<TestPaper> listTestPaperByPage(int pageSize, int pageNo, String departmentId, String testPaperName, String testPaperCategoryId);
    TestPaper saveOrUpdate(TestPaper testPaper);
    void deleteById (Long id);
    TestPaper getById(Long id);
}
