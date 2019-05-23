package com.xinguan.workprocess.process.documentaudit;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhan
 */
@Component
public class DocumentAuditServiceTaskDelegate implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
