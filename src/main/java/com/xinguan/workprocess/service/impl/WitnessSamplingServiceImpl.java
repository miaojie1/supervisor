package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workprocess.model.WitnessSampling;
import com.xinguan.workprocess.service.WitnessSamplingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WitnessSamplingServiceImpl extends BaseService<WitnessSampling> implements WitnessSamplingService {
    @Autowired
    EmployeeService employeeService;

    @Transactional
    @Override
    public WitnessSampling saveWitnessSampling(WitnessSampling witnessSampling){
        return witnessSamplingRepository.save(witnessSampling);
    }

    @Override
    public void deleteWitnessSamplingById(Long id){
        witnessSamplingRepository.deleteById(id);
    }

    @Override
    public Page<WitnessSampling> listWitnessSamplingByDepartment(int pageNo, int pageSize, String samplingName){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        WitnessSampling witnessSampling=new WitnessSampling();
        witnessSampling.setDepartment(currentdepart);
        if (samplingName!=null){
            witnessSampling.setSamplingName("%" + samplingName+ "%");
        }
        witnessSampling.setOriginRank(null);
        Example<WitnessSampling> witnessSamplingExample = getSimpleExample(witnessSampling);
        return witnessSamplingRepository.findAll(witnessSamplingExample, PageRequest.of(pageNo, pageSize));
    }


    @Override
    public WitnessSampling findById(int id){
        return witnessSamplingRepository.findById(new Long((long)id)).get();
    }
}
