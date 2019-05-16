package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.CheckStatus;
import com.xinguan.workprocess.service.CheckStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckStatusServiceImpl extends BaseService<CheckStatus> implements CheckStatusService {

    @Override
    public List<CheckStatus> listAllCheckStatus() {
        return checkStatusRepository.findAll();
    }
}
