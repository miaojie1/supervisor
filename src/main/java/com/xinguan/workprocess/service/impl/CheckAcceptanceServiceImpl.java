package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workprocess.model.CheckAcceptance;
import com.xinguan.workprocess.service.CheckAcceptanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class CheckAcceptanceServiceImpl extends BaseService<CheckAcceptance> implements CheckAcceptanceService {

    @Autowired
    EmployeeService employeeService;

    @Override
    public Page<CheckAcceptance> listCheckAcceptanceByPage(int pageSize, int pageNo, String acceptanceName) {
        CheckAcceptance checkAcceptance = new CheckAcceptance();
        Employee employee = employeeService.getCurrentUser();
        Department department = employee.getDepartment();
        checkAcceptance.setDepartment(department);
        Example<CheckAcceptance> example;
        if (acceptanceName != null) {
            checkAcceptance.setAcceptanceName("%" + acceptanceName + "%");
        }
        example = getSimpleExample(checkAcceptance);
        return checkAcceptanceRepository.findAll(example, PageRequest.of(pageNo, pageSize));
    }

    @Transactional
    @Override
    public CheckAcceptance saveOrUpdate(CheckAcceptance checkAcceptance) {
        Example<CheckAcceptance> conferenceExample = getSimpleExample(checkAcceptance);
        if (checkAcceptance.getId() != null) {
            checkAcceptance.setModificationDate(new Date());
        } else {
            checkAcceptance.setCreater(employeeService.getCurrentUser());
            checkAcceptance.setDepartment(employeeService.getCurrentUser().getDepartment());
            if(employeeService.getCurrentUser().getDepartmentPosition()!=null){
                checkAcceptance.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
            }
            checkAcceptance.setCreateDate(new Date());
        }
        return checkAcceptanceRepository.saveAndFlush(checkAcceptance);
    }

    @Override
    public void removeCheckAcceptance(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        checkAcceptanceRepository.deleteById(id);
    }
}
