package com.xinguan.workprocess.service;

import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.Patrol;

public interface PatrolService {
    Patrol savePatrol(Patrol patrol);
    void checkPatrol(Long patrolId, String taskId, Boolean approved, String auditOpinion);
    void deletePatrolById(Long id);
    PageInfo<Patrol> listPatrolByDepartment(int pageNo, int pageSize, String location);
    Patrol findById(int id);
}
