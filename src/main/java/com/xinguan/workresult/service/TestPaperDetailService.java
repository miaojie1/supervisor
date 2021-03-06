package com.xinguan.workresult.service;

import com.xinguan.workresult.model.TestPaperDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TestPaperDetailService {
    Page<TestPaperDetail> listTestPaperDetailByTestPaperPage(int pageSize, int pageNo, String testPaperDetailName, String testPaperId);
    TestPaperDetail saveOrUpdate(TestPaperDetail testPaperDetail);
    void deleteById (Long id);
    TestPaperDetail getById(Long id);
    List<TestPaperDetail> listAllDetailByTest(String testPaperId);
}
