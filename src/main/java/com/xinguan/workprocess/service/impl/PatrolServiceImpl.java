package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workprocess.model.Patrol;
import com.xinguan.workprocess.service.PatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatrolServiceImpl extends BaseService<Patrol> implements PatrolService {
    @Autowired
    EmployeeService employeeService;

    @Transactional
    @Override
    public Patrol savePatrol(Patrol Patrol){
        return patrolRepository.save(Patrol);
    }

    @Transactional
    @Override
    public void deletePatrolById(Long id){
        patrolRepository.deleteById(id);
    }

    @Override
    public Page<Patrol> listPatrolByDepartment(int pageNo, int pageSize, String location){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        Patrol Patrol=new Patrol();
        Patrol.setDepartment(currentdepart);
        if (location!=null){
            Patrol.setLocation("%" + location+ "%");
        }
        Patrol.setOriginRank(null);
        Example<Patrol> PatrolExample = getSimpleExample(Patrol);
        return patrolRepository.findAll(PatrolExample, PageRequest.of(pageNo, pageSize));
    }


    @Override
    public Patrol findById(int id){
        return patrolRepository.findById(new Long((long)id)).get();
    }
}
