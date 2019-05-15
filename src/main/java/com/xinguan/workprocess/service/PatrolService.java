package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.Patrol;
import org.springframework.data.domain.Page;

public interface PatrolService {
    Patrol savePatrol(Patrol patrol);
    void deletePatrolById(Long id);
    Page<Patrol> listPatrolByDepartment(int pageNo, int pageSize, String location);
    Patrol findById(int id);
}
