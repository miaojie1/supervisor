package com.xinguan.workprocess.service;

import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.ParallelTest;
import org.springframework.data.domain.Page;

public interface ParallelTestService {
    PageInfo<ParallelTest> listParallelTests(int pageNo, int pageSize, String partName);

    ParallelTest saveParallelTest(ParallelTest parallelTest);

    void deleteParallelTestById(long id);

    void checkParallelTest(Long siteAcceptanceId, String taskId, Boolean approved, String auditOpinion);

}
