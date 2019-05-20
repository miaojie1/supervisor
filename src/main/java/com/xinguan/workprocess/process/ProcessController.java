package com.xinguan.workprocess.process;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

}
