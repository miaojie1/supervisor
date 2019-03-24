package com.xinguan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangzhan
 * @date 2019-01-04 20:47
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping
    public String sayHello() {
        return "Hello User";
    }
}
