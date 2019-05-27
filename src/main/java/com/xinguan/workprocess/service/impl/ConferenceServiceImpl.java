package com.xinguan.workprocess.service.impl;

import com.google.common.collect.Maps;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.*;
import com.xinguan.workprocess.service.ConferenceService;
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
import java.util.Set;

@Service
public class ConferenceServiceImpl extends BaseService<Conference> implements ConferenceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    @Autowired
    EmployeeService employeeService;
    @Override
    public PageInfo<Conference> listConferenceByPage(int pageSize, int pageNo, String content) {
        Conference conference = new Conference();
        Employee employee = employeeService.getCurrentUser();
        Department department = employee.getDepartment();
        conference.setDepartment(department);
        if (content != null) {
            conference.setContent("%" + content + "%");
        }
        conference.setOriginRank(null);
        Example<Conference> conferenceExample = getSimpleExample(conference);
        Page<Conference> conferencePage = conferenceRepository.findAll(conferenceExample, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("content",content);
        PageInfo<Conference> pageInfo = new PageInfo<>(conferencePage,param);
        pageInfo.setContent(processConfercence(conferencePage.getContent()));
        return pageInfo;

    }
    @Transactional
    @Override
    public Conference saveOrUpdate(Conference conference) {
        if (conference.getId() != null) {
            conference.setModificationDate(new Date());
        } else {
            conference.setInitiator(employeeService.getCurrentUser());
            conference.setDepartment(employeeService.getCurrentUser().getDepartment());
            if(employeeService.getCurrentUser().getDepartmentPosition()!=null){
                conference.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
            }
            conference.setCreateDate(new Date());
        }
        conference.setAuditStatus("未审核");
        EmployeeAudit employeeAudit = new EmployeeAudit();
        employeeAudit.setEmployee(conference.getProject().getManager());
        employeeAuditRepository.saveAndFlush(employeeAudit);
        conference.setMajorAudit(employeeAudit);
        Conference result = conferenceRepository.saveAndFlush(conference);
        if(1==conference.getIsSubmit()){
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String,Object> param = Maps.newHashMap();
            param.put(ProcessConstant.Conference.NodeVariable.COMMIT_CONFERENCE_ASSIGNMENT,currentUserId);
//            param.put(ProcessConstant.Conference.NodeVariable.SUMMARY_CANDIDATE_USER,result.getEmployeeWriteList());
            param.put(ProcessConstant.Conference.NodeVariable.AUDIT_ASSIGNMENT,result.getProject().getManager().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.Conference.NodeId.PROCESS_KEY, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            conferenceRepository.saveAndFlush(result);
            List<String> userIds = Lists.newArrayList();
            result.getEmployeeWriteList().forEach(e->{
                userIds.add(String.valueOf(e.getId()));
            });
            //完成我的个人任务
            Task task = taskService.createTaskQuery().taskAssignee(currentUserId).processInstanceId(processInstance.getId()).singleResult();
            if (task != null) {
                taskService.setVariable(task.getId(),"userIds",userIds);
                taskService.complete(task.getId());
                LOGGER.info("已完成个人任务");
            }
        }
        return result;
    }

    @Override
    public void removeConference(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        conferenceRepository.deleteById(id);
    }

    @Override
    public void batchRemoveConference(Set<Long> ids) {
    }

    @Override
    public Conference findById(long id) {
        return conferenceRepository.findById(id).get();
    }

    @Override
    public Conference allotWriter(long id, List<Employee> employees) {
        return null;
    }

//    @Override
//    public Conference allotWriter(long id, List<Employee> employees) {
//        Conference conference = findById(id);
//        if (conference != null) {
//            List<ConferenceSummary> employeeWriters = Lists.newArrayList();
//            List<String> userIds = Lists.newArrayList();
//            employees.forEach(e->{
//                ConferenceSummary employeeWriter = new ConferenceSummary();
//                employeeWriter.setEmployee(e);
//                conferenceSummaryRepository.saveAndFlush(employeeWriter);
//                userIds.add(String.valueOf(e.getId()));
//                employeeWriters.add(employeeWriter);
//            });
//            conference.setEmployeeWriteList(employeeWriters);
//            conference.setModificationDate(new Date());
//            conferenceRepository.saveAndFlush(conference);
//            //添加流程变量
//            runtimeService.setVariable(conference.getProcessId(), "userIds", userIds);
//            runtimeService.setVariable(conference.getProcessId(),"conferenceId",conference.getId());
//            //完成我的个人任务
//            Task task = taskService.createTaskQuery().taskAssignee(employeeService.getCurrentUser().getId().toString()).processInstanceId(conference.getProcessId()).singleResult();
//            if (task != null) {
//                taskService.complete(task.getId());
//            }
//        }
//        return conference;
//    }

    @Override
    public void writeSummary(Long conferenceId, String taskId, String content) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if(conference != null){
            ConferenceSummary conferenceSummary = new ConferenceSummary();
            conferenceSummary.setConference(conference);
            conferenceSummary.setEmployee(employee);
            conferenceSummary.setContent(content);
            conferenceSummaryRepository.saveAndFlush(conferenceSummary);
           conferenceRepository.saveAndFlush(conference);
            Map<String,Object> param = Maps.newHashMap();
            param.put(ProcessConstant.Conference.NodeVariable.SUMMARY_CANDIDATE_USER,employee.getId());
           taskService.setVariables(taskId, param);
           taskService.complete(taskId);
            flag = true;
        }
        if (!flag) {
            throw new RuntimeException("填写失败");
        }
    }

    @Override
    public void checkConference(Long conferenceId, String taskId, Boolean approved, String auditOpinion) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (conference != null) {
            if (conference.getMajorAudit() != null) {
                EmployeeAudit employeeAudit = conference.getMajorAudit();
                employeeAudit.setApproved(approved);
                employeeAudit.setAuditOpinion(auditOpinion);
                employeeAudit.setAuditDate(new Date());
                employeeAuditRepository.saveAndFlush(employeeAudit);
                Map<String,Object> param = Maps.newHashMap();
                if (approved) {
                    conference.setAuditStatus("审核通过");
                    param.put(ProcessConstant.Conference.NodeVariable.APPROVE_STR, "true");
                }else{
                    conference.setAuditStatus("未通过");
                    param.put(ProcessConstant.Conference.NodeVariable.APPROVE_STR, "false");
                }
                conferenceRepository.saveAndFlush(conference);
                taskService.setVariables(taskId, param);
                taskService.complete(taskId);
                flag = true;
            }
        }
        if (!flag) {
            throw new RuntimeException("审核失败");
        }
    }

    private List<Conference> processConfercence(List<Conference> conferences) {
        List<Conference> processResult = Lists.newArrayList();
        conferences.stream().filter(e->{
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

                if(ProcessConstant.Conference.NodeId.SUMMARY_FILL.equals(activityId)){
                    e.setNeedWrite(true);
                }else if(ProcessConstant.Conference.NodeId.AUDIT.equals(activityId)){
                    e.setNeedAudit(true);

                }
                e.setTaskId(task.getId());
                processResult.add(e);
            }
        });

        return processResult;
    }
}
