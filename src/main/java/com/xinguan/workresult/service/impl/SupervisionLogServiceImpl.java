package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workprocess.model.ProjectSupervisionDepartment;
import com.xinguan.workresult.model.SupervisionLog;
import com.xinguan.workresult.service.SupervisionLogService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SupervisionLogServiceImpl extends BaseService<SupervisionLog> implements SupervisionLogService {

    @Override
    public Page<SupervisionLog> listSupervisionLogByEmp(int pageNo, int pageSize, String name) {
        Employee employee=null;
        if (name!=""){
            employee = employeeRepository.findByUsername(name);
        }
        SupervisionLog supervisionLog = new SupervisionLog();
        supervisionLog.setSupervisionEngineer(employee);
        Example<SupervisionLog> supervisionLogExample = getSimpleExample(supervisionLog);
        return supervisionLogRepository.findAll(supervisionLogExample,PageRequest.of(pageNo, pageSize));
    }

    @Override
    public Page<SupervisionLog> listSupervisionLogByPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id");
        return supervisionLogRepository.findAll(pageable);
    }

    @Override
    public void deleteSupLogById(Long id) {
        supervisionLogRepository.deleteById(id);
    }

    @Override
    public Set<Employee> listAllSuperEmployees() {
        Set<Employee> employeeList = new HashSet<>();
        List<ProjectSupervisionDepartment> supdeps = projectSupervisionDepartmentRepository.findAll();
        for (ProjectSupervisionDepartment proSupDepart : supdeps) {
            employeeList.add(proSupDepart.getMajor());
        }
        return employeeList;
    }
}
