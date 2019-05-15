package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workprocess.model.SiteAcceptance;
import com.xinguan.workprocess.service.SiteAcceptanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class SiteAcceptanceServiceImpl extends BaseService<SiteAcceptance> implements SiteAcceptanceService {

    @Autowired
    EmployeeService employeeService;

    @Override
    public Page<SiteAcceptance> listSiteAcceptancesByPage(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id");
        return siteAcceptanceRepository.findAll(pageable);
    }

    @Override
    public SiteAcceptance saveSiteAcceptance(SiteAcceptance siteAcceptance){
       return siteAcceptanceRepository.save(siteAcceptance);
    }

    @Override
    public void deleteSiteAcceptById(Long id){
        siteAcceptanceRepository.deleteById(id);
    }

    @Override
    public Page<SiteAcceptance> listSiteAcceptancesByDepart(int pageNo, int pageSize, String materialName){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        SiteAcceptance siteAcceptance=new SiteAcceptance();
        siteAcceptance.setDepartment(currentdepart);
        if (materialName!=""){
            siteAcceptance.setMaterialName("%" + materialName + "%");
        }
        siteAcceptance.setOriginRank(null);
        Example<SiteAcceptance> siteAcceptanceExample = getSimpleExample(siteAcceptance);
        return siteAcceptanceRepository.findAll(siteAcceptanceExample, PageRequest.of(pageNo, pageSize));
    }


    @Override
    public SiteAcceptance findById(int id){
        return siteAcceptanceRepository.findById(new Long((long)id)).get();
    }

}
