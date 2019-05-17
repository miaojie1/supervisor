package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workresult.model.AccountRecord;
import com.xinguan.workresult.service.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AccountRecordServiceImpl extends BaseService<AccountRecord> implements AccountRecordService {
    @Autowired
    EmployeeService employeeService;

    @Override
    public Page<AccountRecord> listAccountRecordByPage(int pageNo,int pageSize, String title){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        AccountRecord accountRecord=new AccountRecord();
        accountRecord.setDepartment(currentdepart);
        if (title!=""){
            accountRecord.setRecordName("%" + title + "%");
        }
        Example<AccountRecord> accountRecordExample = getSimpleExample(accountRecord);
        return accountRecordRepository.findAll(accountRecordExample, PageRequest.of(pageNo, pageSize));
    }

    @Transactional
    @Override
    public AccountRecord saveAccountRecord(AccountRecord accountRecord){
        return accountRecordRepository.saveAndFlush(accountRecord);
    }

    @Override
    public void deleteAccountRecordById(Long id){
        accountRecordRepository.deleteById(id);
    }

    @Override
    public AccountRecord getAccById(Long id){
        return accountRecordRepository.findById(id).get();
    }

    @Override
    public AccountRecord getAccByRecordId(Long id){
        return accountRecordRepository.findAccountRecordByRecordId(id);
    }
}
