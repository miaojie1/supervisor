package com.xinguan.workprocess.process;

import com.xinguan.workprocess.service.ConferenceService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AllotUserWrite implements TaskListener {

    @Autowired
    private ConferenceService conferenceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AllotUserWrite.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        List<String> userIds = (List<String>) delegateTask.getExecution().getVariable("userIds");
        delegateTask.getExecution().setVariable("assigneeList",userIds);
        if (userIds != null && userIds.size() > 0) {
            delegateTask.addCandidateUsers(userIds);
        }
    }
}
