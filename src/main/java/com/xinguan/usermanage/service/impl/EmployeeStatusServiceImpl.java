package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.EmployeeStatus;
import com.xinguan.usermanage.service.EmployeeStatusService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeStatusServiceImpl extends BaseService<EmployeeStatus> implements EmployeeStatusService {

    @Override
    public List<EmployeeStatus> listAllEmployeeStatus() {
        return employeeStatusRepository.findAll();
    }

    @Override
    public Page<EmployeeStatus> listEmployeeStatusByPage(int pageSize, int pageNo) {
        EmployeeStatus employeeStatus = new EmployeeStatus();
        Example<EmployeeStatus> example = getSimpleExample(employeeStatus);
        return employeeStatusRepository.findAll(example, PageRequest.of(pageNo, pageSize));
    }
}
