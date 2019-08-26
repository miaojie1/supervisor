package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.DepartmentPosition;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface DepartmentPositionService {
    Page<DepartmentPosition> listDepartmentPositionByPage (int pageSize, int pageNo);
    //List<DepartmentPosition> listAllDepartmentPositions(Collection<Long> departmentIds);

    List<DepartmentPosition> listAllDepartmentPositions(String departmentIds);
}
