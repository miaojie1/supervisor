package com.xinguan.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangzhan
 * @date 2019-01-04 20:49
 */
@RestController
@RequestMapping("/secure")
@Secured("ROLE_ADMIN")
public class SecureController {

    @GetMapping
    public String sayHello() {
        return "Secure Hello";
    }
}
