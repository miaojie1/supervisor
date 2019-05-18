package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.ParallelTest;
import org.springframework.data.domain.Page;

public interface ParallelTestService {
    Page<ParallelTest> listParallelTests(int pageNo,int pageSize,String partName);

    ParallelTest saveParallelTest(ParallelTest parallelTest);

    void deleteParallelTestById(long id);
}
