package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeStatusService {
    List<EmployeeStatus> listAllEmployeeStatus();
    Page<EmployeeStatus> listEmployeeStatusByPage(int pageSize, int pageNo);
}
