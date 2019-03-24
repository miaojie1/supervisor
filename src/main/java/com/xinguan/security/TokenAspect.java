package com.xinguan.security;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class TokenAspect {

    private final static Logger LOG = LoggerFactory.getLogger(TokenAspect.class);

    @Pointcut("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    private void token() {
    }

    @AfterReturning(returning = "obj", pointcut = "token()")
    public void doAfterReturning(ResponseEntity<OAuth2AccessToken> obj) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");
        LOG.debug("用户" + username + "获取AccessToken:" + obj.getBody().getValue());
    }
}
