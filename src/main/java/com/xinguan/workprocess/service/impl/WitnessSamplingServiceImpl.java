package com.xinguan.workprocess.service.impl;

import com.google.common.collect.Maps;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.workprocess.model.EmployeeAudit;
import com.xinguan.workprocess.model.WitnessSampling;
import com.xinguan.workprocess.service.WitnessSamplingService;
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
public class WitnessSamplingServiceImpl extends BaseService<WitnessSampling> implements WitnessSamplingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WitnessSamplingServiceImpl.class);
    @Autowired
    EmployeeService employeeService;

    @Transactional
    @Override
    public WitnessSampling saveWitnessSampling(WitnessSampling witnessSampling){
        witnessSampling.setAuditStatus("未审核");
        EmployeeAudit employeeAudit = new EmployeeAudit();
        employeeAudit.setEmployee(witnessSampling.getProject().getManager());
        employeeAuditRepository.saveAndFlush(employeeAudit);
        witnessSampling.setMajorAudit(employeeAudit);
        WitnessSampling result = witnessSamplingRepository.saveAndFlush(witnessSampling);

        //如果已经提交，提交见证取样审核流程
        if (1==witnessSampling.getIsSubmit()) {
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String,Object> param = Maps.newHashMap();
            param.put(ProcessConstant.WitnessSampling.NodeVariable.COMMIT_ASSIGNMENT, currentUserId);
            param.put(ProcessConstant.WitnessSampling.NodeVariable.SEND_TO_CHARGE, true);
            param.put(ProcessConstant.WitnessSampling.NodeVariable.AUDIT_ASSIGNMENT, result.getProject().getManager().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.WitnessSampling.NodeId.PROCESS_KEY, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            witnessSamplingRepository.saveAndFlush(result);
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
    public void checkWitnessSampling(Long witnessSamplingId, String taskId, Boolean approved, String auditOpinion) {
        WitnessSampling witnessSampling = witnessSamplingRepository.findById(witnessSamplingId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (witnessSampling != null) {
            if (witnessSampling.getMajorAudit() != null) {
                EmployeeAudit employeeAudit = witnessSampling.getMajorAudit();
                employeeAudit.setApproved(approved);
                employeeAudit.setAuditOpinion(auditOpinion);
                employeeAudit.setAuditDate(new Date());
                employeeAuditRepository.saveAndFlush(employeeAudit);
                Map<String,Object> param = Maps.newHashMap();
                if (approved) {
                    witnessSampling.setAuditStatus("审核通过");
                }else{
                    witnessSampling.setAuditStatus("未通过");
                }
                witnessSamplingRepository.saveAndFlush(witnessSampling);
                taskService.setVariables(taskId, param);
                taskService.complete(taskId);
                flag = true;
            }
        }
        if (!flag) {
            throw new RuntimeException("审核失败");
        }
    }
    @Override
    public void deleteWitnessSamplingById(Long id){
        witnessSamplingRepository.deleteById(id);
    }

    @Override
    public PageInfo<WitnessSampling> listWitnessSamplingByDepartment(int pageNo, int pageSize, String samplingName){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        WitnessSampling witnessSampling=new WitnessSampling();
        witnessSampling.setDepartment(currentdepart);
        if (samplingName!=null){
            witnessSampling.setSamplingName("%" + samplingName+ "%");
        }
        witnessSampling.setOriginRank(null);
        Example<WitnessSampling> witnessSamplingExample = getSimpleExample(witnessSampling);
        Page<WitnessSampling> witnessSamplingPage = witnessSamplingRepository.findAll(witnessSamplingExample, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("samplingName",samplingName);
        PageInfo<WitnessSampling> pageInfo = new PageInfo<>(witnessSamplingPage,param);
        pageInfo.setContent(processWitnessSampling(witnessSamplingPage.getContent()));
        return pageInfo;
    }
    private List<WitnessSampling> processWitnessSampling(List<WitnessSampling> witnessSamplings) {
        List<WitnessSampling> processResult = Lists.newArrayList();
        witnessSamplings.stream().filter(e->{
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
                if(ProcessConstant.WitnessSampling.NodeId.AUDIT.equals(activityId)){
                    e.setNeedAudit(true);
                }
                e.setTaskId(task.getId());
                processResult.add(e);
            }
        });

        return processResult;
    }

    @Override
    public WitnessSampling findById(int id){
        return witnessSamplingRepository.findById(new Long((long)id)).get();
    }
}
