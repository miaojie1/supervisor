package com.xinguan.workprocess.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parallelTest")
@Api(value = "平行检验相关接口")
public class ParallelTestController extends WorkProcessBaseController{
}