package com.xinguan.workprocess.service.impl;

import com.google.common.collect.Maps;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.workprocess.model.EmployeeAudit;
import com.xinguan.workprocess.model.SideStation;
import com.xinguan.workprocess.service.SideStationService;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SideStationServiceImpl extends BaseService<SideStation> implements SideStationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentAuditServiceImpl.class);

    @Autowired
    EmployeeService employeeService;

    @Override
    public PageInfo<SideStation> listSideStations(int pageNo, int pageSize, String partName) {
        SideStation sideStation = new SideStation();
        if (partName != "") {
            sideStation.setPart("%" + partName + "%");
        }
        Example<SideStation> sideStationExample = getSimpleExample(sideStation);
        Page<SideStation> sideStationPage = sideStationRepository.findAll(sideStationExample, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("partName",partName);
        PageInfo<SideStation> pageInfo = new PageInfo<>(sideStationPage,param);
        pageInfo.setContent(processSideStation(sideStationPage.getContent()));
        return pageInfo;
    }

    private List<SideStation> processSideStation(List<SideStation> sideStations) {
        List<SideStation> processResult = Lists.newArrayList();
        sideStations.stream().filter(e->{
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
                if(ProcessConstant.ParallelTest.NodeId.AUDIT.equals(activityId)){
                    e.setNeedAudit(true);
                }
                e.setTaskId(task.getId());
                processResult.add(e);
            }
        });

        return processResult;
    }


    @Override
    public SideStation saveSideStation(SideStation sideStation) {
        sideStation.setAuditStatus("未审核");
        EmployeeAudit employeeAudit = new EmployeeAudit();
        employeeAudit.setEmployee(sideStation.getProject().getManager());
        employeeAuditRepository.saveAndFlush(employeeAudit);
        sideStation.setMajorAudit(employeeAudit);
        SideStation result = sideStationRepository.save(sideStation);

        //如果已经提交，提交文件审核流程
        if (1 == sideStation.getIsSubmit()) {
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String, Object> param = Maps.newHashMap();
            param.put(ProcessConstant.SiteAcceptance.NodeVariable.COMMIT_ASSIGNMENT, currentUserId);
            param.put(ProcessConstant.SiteAcceptance.NodeVariable.SEND_TO_CHARGE, true);
            param.put(ProcessConstant.SiteAcceptance.NodeVariable.AUDIT_ASSIGNMENT, result.getProject().getManager().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.SideStation.NodeId.PROCESS_KEY, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            sideStationRepository.save(result);
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
    public void deleteSideStationById(long id) {
        sideStationRepository.deleteById(id);
    }

    @Override
    public void checkSideStation(Long sideStationId, String taskId, Boolean approved, String auditOpinion) {
        SideStation sideStation = sideStationRepository.findById(sideStationId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (sideStation != null) {
            if (sideStation.getMajorAudit() != null) {
                EmployeeAudit employeeAudit = sideStation.getMajorAudit();
                employeeAudit.setApproved(approved);
                employeeAudit.setAuditOpinion(auditOpinion);
                employeeAudit.setAuditDate(new Date());
                employeeAuditRepository.saveAndFlush(employeeAudit);
                Map<String, Object> param = Maps.newHashMap();
                if (approved) {
                    sideStation.setAuditStatus("审核通过");
//                        param.put(ProcessConstant.SiteAcceptance.NodeVariable., "true");
                } else {
                    sideStation.setAuditStatus("未通过");
//                        param.put(ProcessConstant.DocumentAudit.NodeVariable.APPROVED, "false");
                }
                sideStationRepository.saveAndFlush(sideStation);
                taskService.setVariables(taskId, param);
                taskService.complete(taskId);
                flag = true;
            }
        }

    }
}