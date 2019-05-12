package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Operation;
import com.xinguan.usermanage.service.OperationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OperationServiceImpl extends BaseService<Operation> implements OperationService {
    @Override
    public List<Operation> findOperationByButtonId(String name){
        return operationRepository.findOperationsByButtonId(name);
    }
}
