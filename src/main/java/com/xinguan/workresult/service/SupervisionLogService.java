package com.xinguan.workresult.service;

import com.xinguan.workresult.model.SupervisionLog;
import org.springframework.data.domain.Page;

public interface SupervisionLogService {
    Page<SupervisionLog> listSupervisionLogByPage(int pageNo, int pageSize);
    Page<SupervisionLog> listSupervisionLogByEmp(int pageNo, int pageSize, String name);
}
