package com.xinguan.workresult.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.SupervisionLog;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface SupervisionLogService {
    /**
     * 分页获取监理日志
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<SupervisionLog> listSupervisionLogByPage(int pageNo, int pageSize);

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param name 监理工程师用户名
     * @return
     */
    Page<SupervisionLog> listSupervisionLogByEmp(int pageNo, int pageSize, String name);

    /**
     * 获取所有的监理工程师
     * @return
     */
    Set<Employee> listAllSuperEmployees();
    void deleteSupLogById(Long id);
}
