package com.xinguan.workprocess.service.impl;

import com.google.common.collect.Maps;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.workprocess.model.EmployeeAudit;
import com.xinguan.workprocess.model.Patrol;
import com.xinguan.workprocess.service.PatrolService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatrolServiceImpl extends BaseService<Patrol> implements PatrolService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatrolServiceImpl.class);
    @Autowired
    EmployeeService employeeService;

    @Transactional
    @Override
    public Patrol savePatrol(Patrol patrol){
        patrol.setAuditStatus("未审核");
        EmployeeAudit employeeAudit = new EmployeeAudit();
        employeeAudit.setEmployee(patrol.getProject().getManager());
        employeeAuditRepository.saveAndFlush(employeeAudit);
        patrol.setMajorAudit(employeeAudit);
        Patrol result = patrolRepository.save(patrol);

        //如果已经提交，提交巡视审核流程
        if (1==patrol.getIsSubmit()) {
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String,Object> param = Maps.newHashMap();
            param.put(ProcessConstant.Patrol.NodeVariable.COMMIT_ASSIGNMENT, currentUserId);
            param.put(ProcessConstant.Patrol.NodeVariable.SEND_TO_CHARGE, true);
            param.put(ProcessConstant.Patrol.NodeVariable.AUDIT_ASSIGNMENT, result.getProject().getManager().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.Patrol.NodeId.PROCESS_KEY, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            patrolRepository.save(result);
            //完成我的个人任务
            Task task = taskService.createTaskQuery().taskAssignee(currentUserId).processInstanceId(processInstance.getId()).singleResult();
            if (task != null) {
                taskService.complete(task.getId());
                LOGGER.info("已完成个人任务");
            }
        }
        return result;
    }
    @Override
    public void checkPatrol(Long patrolId, String taskId, Boolean approved, String auditOpinion) {
        Patrol patrol = patrolRepository.findById(patrolId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (patrol != null) {
            if (patrol.getMajorAudit() != null) {
                EmployeeAudit employeeAudit = patrol.getMajorAudit();
                employeeAudit.setApproved(approved);
                employeeAudit.setAuditOpinion(auditOpinion);
                employeeAudit.setAuditDate(new Date());
                employeeAuditRepository.saveAndFlush(employeeAudit);
                Map<String,Object> param = Maps.newHashMap();
                if (approved) {
                    patrol.setAuditStatus("审核通过");
                }else{
                    patrol.setAuditStatus("未通过");
                }
                patrolRepository.saveAndFlush(patrol);
                taskService.setVariables(taskId, param);
                taskService.complete(taskId);
                flag = true;
            }
        }
        if (!flag) {
            throw new RuntimeException("审核失败");
        }
    }
    @Transactional
    @Override
    public void deletePatrolById(Long id){
        patrolRepository.deleteById(id);
    }

    @Override
    public PageInfo<Patrol> listPatrolByDepartment(int pageNo, int pageSize, String location){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        Patrol Patrol=new Patrol();
        Patrol.setDepartment(currentdepart);
        if (location!=null){
            Patrol.setLocation("%" + location+ "%");
        }
        Patrol.setOriginRank(null);
        Example<Patrol> patrolExample = getSimpleExample(Patrol);
        Page<Patrol> patrolPage = patrolRepository.findAll(patrolExample, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("location",location);
        PageInfo<Patrol> pageInfo = new PageInfo<>(patrolPage,param);
        pageInfo.setContent(processPatrol(patrolPage.getContent()));
        return pageInfo;
    }
    private List<Patrol> processPatrol(List<Patrol> patrols) {
        List<Patrol> processResult = Lists.newArrayList();
        patrols.stream().filter(e->{
            boolean flag = false;
            if (StringUtils.isNotBlank(e.getProcessId())) {
                flag = true;
            }else{
                processResult.add(e);
            }
            return flag;
        }).forEach(e->{
            String userId = String.valueOf(employeeService.getCurrentUser().getId());
            Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userId).processInstanceId(e.getProcessId()).singleResult();
            //如果task为空，则添加到结果列表中，否则获取当前流程的执行节点
            if (task == null) {
                processResult.add(e);
            }else{
                Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
                String activityId = execution.getActivityId();
                //判断当前流程所到达的节点
                if(ProcessConstant.Patrol.NodeId.AUDIT.equals(activityId)){
                    e.setNeedAudit(true);
                }
                e.setTaskId(task.getId());
                processResult.add(e);
            }
        });

        return processResult;
    }

    @Override
    public Patrol findById(int id){
        return patrolRepository.findById(new Long((long)id)).get();
    }
}
