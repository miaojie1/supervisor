package com.xinguan.workprocess.process;

import com.xinguan.workprocess.model.PersonalTask;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.task.Task;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangzhan
 */
@RestController
public class ProcessController {

    @Autowired
    private ActUtils actUtils;

    @ApiOperation("获取流程图片")
    @GetMapping(value = "/getFlowImg/{processInstanceId}")
    @ResponseBody
    public void getFlowImgByInstantId(@ApiParam(name = "processInstanceId", required = true, value = "流程实例ID")
                                      @PathVariable("processInstanceId") String processInstanceId, HttpServletResponse response) {
        try {
            System.out.println("processInstanceId:" + processInstanceId);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            actUtils.getFlowImgByInstanceId(processInstanceId, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("查询个人任务")
    @PostMapping(value = "/getMyTaskList")
    public List<PersonalTask> getMyTaskList(
            @ApiParam(name = "userId", required = true, value = "用户ID") @RequestParam String userId){
        List<org.activiti.engine.task.Task> res = actUtils.getMyTaskList(userId);
        List<PersonalTask> personalTasks = Lists.newArrayList();
        if (res != null) {
            res.forEach(e->{
                PersonalTask personalTask = new PersonalTask();
                personalTask.setId(e.getId());
                personalTask.setUserId(e.getAssignee());
                personalTask.setName(e.getName());
                personalTask.setPriority(e.getPriority());
                personalTask.setCreateTime(e.getCreateTime());
                personalTask.setExecutionId(e.getExecutionId());
                personalTask.setProcessInstanceId(e.getProcessInstanceId());
                personalTask.setTaskDefinitionKey(e.getTaskDefinitionKey());
                personalTasks.add(personalTask);
            });
        }
        return personalTasks;
    }

}
