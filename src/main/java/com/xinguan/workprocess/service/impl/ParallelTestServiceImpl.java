package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.ParallelTest;
import com.xinguan.workprocess.service.ParallelTestService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ParallelTestServiceImpl extends BaseService<ParallelTest> implements ParallelTestService {
    @Override
    public Page<ParallelTest> listParallelTests(int pageNo, int pageSize, String partName) {
        ParallelTest parallelTest=new ParallelTest();
        if (partName!=""){
            parallelTest.setPart("%"+partName+"%");
        }
        Example<ParallelTest> parallelTestExample = getSimpleExample(parallelTest);
        return parallelTestRepository.findAll(parallelTestExample, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public ParallelTest saveParallelTest(ParallelTest parallelTest) {
        return parallelTestRepository.saveAndFlush(parallelTest);
    }

    @Override
    public void deleteParallelTestById(long id) {
        parallelTestRepository.deleteById(id);
    }
}
