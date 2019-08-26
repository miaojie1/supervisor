package com.xinguan.workprocess.process.documentaudit;

import com.xinguan.workprocess.service.DocumentAuditService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangzhan
 */
@Component
public class AllotUserAudit implements TaskListener {

    @Autowired
    private DocumentAuditService documentAuditService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AllotUserAudit.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        List<String> userIds = (List<String>) delegateTask.getExecution().getVariable("userIds");
        delegateTask.getExecution().setVariable("assigneeList",userIds);
        if (userIds != null && userIds.size() > 0) {
            delegateTask.addCandidateUsers(userIds);
        }
    }
}
