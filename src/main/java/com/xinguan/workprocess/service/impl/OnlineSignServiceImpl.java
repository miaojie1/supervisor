package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workprocess.model.OnlineSign;
import com.xinguan.workprocess.service.OnlineSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OnlineSignServiceImpl extends BaseService<OnlineSign> implements OnlineSignService {
    @Autowired
    EmployeeService employeeService;

    @Transactional
    @Override
    public OnlineSign saveOnlineSign(OnlineSign onlineSign){
        return onlineSignRepository.saveAndFlush(onlineSign);
    }

    @Override
    public void deleteOnlineSignById(Long id){
        onlineSignRepository.deleteById(id);
    }

    @Override
    public Page<OnlineSign> listOnlineSignByDepart(int pageNo, int pageSize, String name){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        OnlineSign onlineSign=new OnlineSign();
        onlineSign.setDepartment(currentdepart);
        if (name!=""){
            onlineSign.setSignName("%" + name + "%");
        }
        onlineSign.setOriginRank(null);
        Example<OnlineSign> onlineSignExample = getSimpleExample(onlineSign);
        return onlineSignRepository.findAll(onlineSignExample, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public OnlineSign findById(long id){
        return onlineSignRepository.findById(id).get();
    }
}
