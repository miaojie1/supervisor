package com.xinguan.workprocess.service.impl;

import com.google.common.collect.Maps;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.workprocess.model.EmployeeAudit;
import com.xinguan.workprocess.model.SiteAcceptance;
import com.xinguan.workprocess.service.SiteAcceptanceService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class SiteAcceptanceServiceImpl extends BaseService<SiteAcceptance> implements SiteAcceptanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentAuditServiceImpl.class);

    @Autowired
    EmployeeService employeeService;

    @Override
    public Page<SiteAcceptance> listSiteAcceptancesByPage(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id");
        return siteAcceptanceRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public SiteAcceptance saveSiteAcceptance(SiteAcceptance siteAcceptance){
        siteAcceptance.setAuditStatus("未审核");
        EmployeeAudit employeeAudit = new EmployeeAudit();
        employeeAudit.setEmployee(siteAcceptance.getProject().getManager());
        employeeAuditRepository.saveAndFlush(employeeAudit);
        siteAcceptance.setMajorAudit(employeeAudit);
        SiteAcceptance result = siteAcceptanceRepository.save(siteAcceptance);

        //如果已经提交，提交文件审核流程
        if (1==siteAcceptance.getIsSubmit()) {
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String,Object> param = Maps.newHashMap();
            param.put(ProcessConstant.SiteAcceptance.NodeVariable.COMMIT_ASSIGNMENT, currentUserId);
            param.put(ProcessConstant.SiteAcceptance.NodeVariable.SEND_TO_CHARGE, true);
            param.put(ProcessConstant.SiteAcceptance.NodeVariable.AUDIT_ASSIGNMENT, result.getProject().getManager().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.SiteAcceptance.NodeId.PROCESS_KEY, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            siteAcceptanceRepository.save(result);
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
    public void checkSiteAccept(Long siteAcceptanceId, String taskId, Boolean approved, String auditOpinion) {
        SiteAcceptance siteAcceptance = siteAcceptanceRepository.findById(siteAcceptanceId).get();
        Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (siteAcceptance != null) {
            if (siteAcceptance.getMajorAudit() != null) {
                EmployeeAudit employeeAudit = siteAcceptance.getMajorAudit();
                employeeAudit.setApproved(approved);
                employeeAudit.setAuditOpinion(auditOpinion);
                employeeAudit.setAuditDate(new Date());
                employeeAuditRepository.saveAndFlush(employeeAudit);
                Map<String,Object> param = Maps.newHashMap();
                if (approved) {
                    siteAcceptance.setAuditStatus("审核通过");
//                        param.put(ProcessConstant.SiteAcceptance.NodeVariable., "true");
                }else{
                    siteAcceptance.setAuditStatus("未通过");
//                        param.put(ProcessConstant.DocumentAudit.NodeVariable.APPROVED, "false");
                }
                siteAcceptanceRepository.saveAndFlush(siteAcceptance);
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
    public void deleteSiteAcceptById(Long id){
        siteAcceptanceRepository.deleteById(id);
    }

    @Override
    public PageInfo<SiteAcceptance> listSiteAcceptancesByDepart(int pageNo, int pageSize, String materialName){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        SiteAcceptance siteAcceptance=new SiteAcceptance();
        siteAcceptance.setDepartment(currentdepart);
        if (materialName!=""){
            siteAcceptance.setMaterialName("%" + materialName + "%");
        }
        siteAcceptance.setOriginRank(null);
        Example<SiteAcceptance> siteAcceptanceExample = getSimpleExample(siteAcceptance);
        Page<SiteAcceptance> siteAcceptancePage = siteAcceptanceRepository.findAll(siteAcceptanceExample, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("materialName",materialName);
        PageInfo<SiteAcceptance> pageInfo = new PageInfo<>(siteAcceptancePage,param);
        pageInfo.setContent(processSiteAccept(siteAcceptancePage.getContent()));
        return pageInfo;
    }

    private List<SiteAcceptance> processSiteAccept(List<SiteAcceptance> siteAcceptances) {
        List<SiteAcceptance> processResult = Lists.newArrayList();
        siteAcceptances.stream().filter(e->{
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
                if(ProcessConstant.SiteAcceptance.NodeId.AUDIT.equals(activityId)){
                    e.setNeedAudit(true);
                }
                e.setTaskId(task.getId());
                processResult.add(e);
            }
        });

        return processResult;
    }

    @Override
    public SiteAcceptance findById(int id){
        return siteAcceptanceRepository.findById(new Long((long)id)).get();
    }

}
