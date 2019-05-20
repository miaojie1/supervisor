package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.workprocess.model.EmployeeAudit;
import com.xinguan.workprocess.model.ParallelTest;
import com.xinguan.workprocess.service.ParallelTestService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import com.google.common.collect.Maps;
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
public class ParallelTestServiceImpl extends BaseService<ParallelTest> implements ParallelTestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParallelTestService.class);

    @Autowired
    EmployeeService employeeService;

    @Override
    public PageInfo<ParallelTest> listParallelTests(int pageNo, int pageSize, String partName) {
        ParallelTest parallelTest=new ParallelTest();
        if (partName!=""){
            parallelTest.setPart("%"+partName+"%");
        }
        Example<ParallelTest> parallelTestExample = getSimpleExample(parallelTest);
        Page<ParallelTest> parallelTestPage =  parallelTestRepository.findAll(parallelTestExample, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("partName",partName);
        PageInfo<ParallelTest> pageInfo = new PageInfo<>(parallelTestPage,param);
        pageInfo.setContent(processParallelTest(parallelTestPage.getContent()));
        return pageInfo;
    }

    private List<ParallelTest> processParallelTest(List<ParallelTest> parallelTests) {
        List<ParallelTest> processResult = Lists.newArrayList();
        parallelTests.stream().filter(e->{
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
    public void checkParallelTest(Long parallelTestId, String taskId, Boolean approved, String auditOpinion) {
        ParallelTest parallelTest = parallelTestRepository.findById(parallelTestId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (parallelTest != null) {
            if (parallelTest.getMajorAudit() != null) {
                EmployeeAudit employeeAudit = parallelTest.getMajorAudit();
                employeeAudit.setApproved(approved);
                employeeAudit.setAuditOpinion(auditOpinion);
                employeeAudit.setAuditDate(new Date());
                employeeAuditRepository.saveAndFlush(employeeAudit);
                Map<String,Object> param = Maps.newHashMap();
                if (approved) {
                    parallelTest.setAuditStatus("审核通过");
//                        param.put(ProcessConstant.SiteAcceptance.NodeVariable., "true");
                }else{
                    parallelTest.setAuditStatus("未通过");
//                        param.put(ProcessConstant.DocumentAudit.NodeVariable.APPROVED, "false");
                }
                parallelTestRepository.saveAndFlush(parallelTest);
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
    public ParallelTest saveParallelTest(ParallelTest parallelTest) {
        parallelTest.setAuditStatus("未审核");
        EmployeeAudit employeeAudit = new EmployeeAudit();
        employeeAudit.setEmployee(parallelTest.getProject().getManager());
        employeeAuditRepository.saveAndFlush(employeeAudit);
        parallelTest.setMajorAudit(employeeAudit);
        ParallelTest result = parallelTestRepository.saveAndFlush(parallelTest);

        //如果已经提交，提交文件审核流程
        if (1==parallelTest.getIsSubmit()) {
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String,Object> param = Maps.newHashMap();
            param.put(ProcessConstant.ParallelTest.NodeVariable.COMMIT_ASSIGNMENT, currentUserId);
            param.put(ProcessConstant.ParallelTest.NodeVariable.SEND_TO_CHARGE, true);
            param.put(ProcessConstant.ParallelTest.NodeVariable.AUDIT_ASSIGNMENT, result.getProject().getManager().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.ParallelTest.NodeId.PROCESS_KEY, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            parallelTestRepository.saveAndFlush(result);
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
    public void deleteParallelTestById(long id) {
        parallelTestRepository.deleteById(id);
    }
}
