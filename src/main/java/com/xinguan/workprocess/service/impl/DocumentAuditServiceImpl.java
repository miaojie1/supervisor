package com.xinguan.workprocess.service.impl;

import com.google.common.collect.Maps;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ProcessConstant;
import com.xinguan.workprocess.model.DocumentAudit;
import com.xinguan.workprocess.model.EmployeeAudit;
import com.xinguan.workprocess.service.DocumentAuditService;
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
public class DocumentAuditServiceImpl extends BaseService<DocumentAudit> implements DocumentAuditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentAuditServiceImpl.class);

    @Autowired
    EmployeeService employeeService;

    @Override
    public PageInfo<DocumentAudit> listDocumentAuditByDocName(int pageNo, int pageSize, String name){
        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        DocumentAudit documentAudit=new DocumentAudit();
        if (name != "") {
            documentAudit.setTitle("%" + name + "%");
        }
        documentAudit.setDepartment(currentdepart);
        documentAudit.setOriginRank(null);
        Example<DocumentAudit> documentAuditExample = getSimpleExample(documentAudit);
        Page<DocumentAudit> auditPage = documentAuditRepository.findAll(documentAuditExample, PageRequest.of(pageNo, pageSize));
        Map<String,Object> param = Maps.newHashMap();
        param.put("name",name);
        PageInfo<DocumentAudit> pageInfo = new PageInfo<>(auditPage,param);
        pageInfo.setContent(processDocument(auditPage.getContent()));
        return pageInfo;
    }


    private List<DocumentAudit> processDocument(List<DocumentAudit> documentAudits) {
        List<DocumentAudit> processResult = Lists.newArrayList();
        documentAudits.stream().filter(e->{
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
                if (ProcessConstant.DocumentAudit.NodeId._distribution.equals(activityId)) {
                    e.setNeedAllot(true);
                }else if(ProcessConstant.DocumentAudit.NodeId.audit.equals(activityId)
                    || ProcessConstant.DocumentAudit.NodeId.major_audit.equals(activityId)){
                    e.setNeedAudit(true);
                }
                e.setTaskId(task.getId());
                processResult.add(e);
            }
        });

        return processResult;
    }

    @Override
    public DocumentAudit findById(long id){
        return documentAuditRepository.findById(id).get();
    }

    @Transactional
    @Override
    public DocumentAudit saveDocumentAudit(DocumentAudit documentAudit){
        documentAudit.setAuditStatus("未审核");
        DocumentAudit result = documentAuditRepository.saveAndFlush(documentAudit);

        //如果已经提交，提交文件审核流程
        if (1==documentAudit.getIsSubmit()) {
            String currentUserId = String.valueOf(employeeService.getCurrentUser().getId());
            //设置启动流程所属变量
            Map<String,Object> param = Maps.newHashMap();
            param.put(ProcessConstant.DocumentAudit.NodeVariable.upload_file,currentUserId);
            param.put(ProcessConstant.DocumentAudit.NodeVariable.major_audit,result.getProject().getProjectSupervisionDepartment().getMajor().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstant.DocumentAudit.NodeId.process_Key, param);
            result.setProcessId(processInstance.getId());
            result.setAuditStatus("进行中");
            documentAuditRepository.saveAndFlush(result);
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
    public void deleteDocAuditById(long id){
        documentAuditRepository.deleteById(id);
    }

    @Transactional
    @Override
    public DocumentAudit allotAuditTask(long id, List<Employee> employees) {
        DocumentAudit documentAudit = findById(id);
        if (documentAudit != null) {
            List<EmployeeAudit> employeeAudits = Lists.newArrayList();
            List<String> userIds = Lists.newArrayList();
            employees.forEach(e->{
                EmployeeAudit employeeAudit = new EmployeeAudit();
                employeeAudit.setEmployee(e);
                employeeAuditRepository.saveAndFlush(employeeAudit);
                userIds.add(String.valueOf(e.getId()));
                employeeAudits.add(employeeAudit);
            });
            documentAudit.setEmployeeAuditList(employeeAudits);
            documentAudit.setModificationDate(new Date());
            documentAuditRepository.saveAndFlush(documentAudit);
            //添加流程变量
            runtimeService.setVariable(documentAudit.getProcessId(), "userIds", userIds);
            runtimeService.setVariable(documentAudit.getProcessId(),"documentAuditId",documentAudit.getId());
            //完成我的个人任务
            Task task = taskService.createTaskQuery().taskAssignee(employeeService.getCurrentUser().getId().toString()).processInstanceId(documentAudit.getProcessId()).singleResult();
            if (task != null) {
                taskService.complete(task.getId());
            }
        }
        return documentAudit;
    }

}
