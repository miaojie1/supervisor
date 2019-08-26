package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.controller.BaseController;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.DepartmentPosition;
import com.xinguan.usermanage.repository.DepartmentRepository;
import com.xinguan.usermanage.service.DepartmentPositionService;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentPositionServiceImpl extends BaseService<DepartmentPosition> implements DepartmentPositionService {

    @Override
    public List<DepartmentPosition> listAllDepartmentPositions(String departmentIds) {
        if(departmentIds!=null){
            String[] ids = departmentIds.split(",");
            Set<String> idStr = Sets.newHashSet(ids);
            Set<Long> idLong = idStr.stream().map(Long::parseLong).collect(Collectors.toSet());
            return departmentPositionRepository.finAllByDepartment(idLong);
        }else{
            return departmentPositionRepository.findAll();
        }

    }

    @Override
    public Page<DepartmentPosition> listDepartmentPositionByPage(int pageSize, int pageNo) {
        DepartmentPosition departmentPosition = new DepartmentPosition();
        Example<DepartmentPosition> example = getSimpleExample(departmentPosition);
        return departmentPositionRepository.findAll(example, PageRequest.of(pageNo, pageSize));
    }
}
