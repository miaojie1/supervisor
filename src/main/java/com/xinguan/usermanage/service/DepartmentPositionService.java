package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.DepartmentPosition;
import org.springframework.data.domain.Page;

public interface DepartmentPositionService {
    Page<DepartmentPosition> listDepartmentPositionByPage (int pageSize, int pageNo);
}
