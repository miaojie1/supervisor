package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface EmployeeStatusService {
    Page<EmployeeStatus> listEmployeeStatusByPage(int pageSize, int pageNo);
}
