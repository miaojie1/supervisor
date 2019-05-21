package com.xinguan.workprocess.service.impl;

import com.google.common.collect.Maps;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.workprocess.model.CheckAcceptance;
import com.xinguan.workprocess.model.EmployeeAudit;
import com.xinguan.workprocess.service.CheckAcceptanceService;
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
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CheckAcceptanceServiceImpl extends BaseService<CheckAcceptance> implements CheckAcceptanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckAcceptanceServiceImpl.class);
    @Autowired
    EmployeeService employeeService;

    @Override
    public PageInfo<CheckAcceptance> listCheckAcceptanceByPage(int pageSize, int pageNo, String acceptanceName) {
        CheckAcceptance checkAcceptance = new CheckAcceptance();
        Employee employee = employeeService.getCurrentUser();
        Department department = employee.getDepartment();
        checkAcceptance.setDepartment(department);
        Example<CheckAcceptance> example;
        if (acceptanceName != null) {
            checkAcceptance.setAcceptanceName("%" + acceptanceName + "%");
        }
        example = getSimpleExample(checkAcceptance);
        Page<CheckAcceptance> checkAcceptancePage =  checkAcceptanceRepository.findAll(example, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("acceptanceName",acceptanceName);
        PageInfo<CheckAcceptance> pageInfo = new PageInfo<>(checkAcceptancePage,param);
        pageInfo.setContent(processCheckAcceptance(checkAcceptancePage.getContent()));
        return pageInfo;
    }

    @Transactional
    @Override
    public CheckAcceptance saveOrUpdate(CheckAcceptance checkAcceptance) {
        Example<CheckAcceptance> conferenceExample = getSimpleExample(checkAcceptance);
        if (checkAcceptance.getId() != null) {
            checkAcceptance.setModificationDate(new Date());
        } else {
            checkAcceptance.setCreater(employeeService.getCurrentUser());
            checkAcceptance.setDepartment(employeeService.getCurrentUser().getDepartment());
            if(employeeService.getCurrentUser().getDepartmentPosition()!=null){
                checkAcceptance.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
            }
            checkAcceptance.setCreateDate(new Date());
        }
        checkAcceptance.setAuditStatus("未审核");
        EmployeeAudit employeeAudit = new EmployeeAudit();
        employeeAudit.setEmployee(checkAcceptance.getProject().getManager());
        employeeAuditRepository.saveAndFlush(employeeAudit);
        checkAcceptance.setMajorAudit(employeeAudit);
        CheckAcceptance result = checkAcceptanceRepository.saveAndFlush(checkAcceptance);
        if(1==checkAcceptance.getIsSubmit()){
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String,Object> param = Maps.newHashMap();

            param.put(ProcessConstant.CheckAcceptance.NodeVariable.COMMIT_ASSIGNMENT, currentUserId);
            param.put(ProcessConstant.CheckAcceptance.NodeVariable.SEND_TO_CHARGE, true);
            param.put(ProcessConstant.CheckAcceptance.NodeVariable.AUDIT_ASSIGNMENT, result.getProject().getManager().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.CheckAcceptance.NodeId.PROCESS_KEY, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            checkAcceptanceRepository.saveAndFlush(result);
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
    public void removeCheckAcceptance(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        checkAcceptanceRepository.deleteById(id);
    }

    @Override
    public void checkCheckAcceptance(Long checkAcceptanceId, String taskId, Boolean approved, String auditOpinion) {
        CheckAcceptance checkAcceptance = checkAcceptanceRepository.findById(checkAcceptanceId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (checkAcceptance != null) {
            if (checkAcceptance.getMajorAudit() != null) {
                EmployeeAudit employeeAudit = checkAcceptance.getMajorAudit();
                employeeAudit.setApproved(approved);
                employeeAudit.setAuditOpinion(auditOpinion);
                employeeAudit.setAuditDate(new Date());
                employeeAuditRepository.saveAndFlush(employeeAudit);
                Map<String,Object> param = Maps.newHashMap();
                if (approved) {
                    checkAcceptance.setAuditStatus("审核通过");
                }else{
                    checkAcceptance.setAuditStatus("未通过");
                }
                checkAcceptanceRepository.saveAndFlush(checkAcceptance);
                taskService.setVariables(taskId, param);
                taskService.complete(taskId);
                flag = true;
            }
        }
        if (!flag) {
            throw new RuntimeException("审核失败");
        }
    }

    private List<CheckAcceptance> processCheckAcceptance(List<CheckAcceptance> checkAcceptances) {
        List<CheckAcceptance> processResult = Lists.newArrayList();
        checkAcceptances.stream().filter(e->{
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
                if(ProcessConstant.CheckAcceptance.NodeId.AUDIT.equals(activityId)){
                    e.setNeedAudit(true);
                }
                e.setTaskId(task.getId());
                processResult.add(e);
            }
        });

        return processResult;
    }
}
