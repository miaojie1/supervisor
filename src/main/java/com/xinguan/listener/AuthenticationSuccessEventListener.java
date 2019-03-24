package com.xinguan.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


/**
 * @author zhangzhan
 * @date 2019-02-16 16:12
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {


    private final static Logger LOG = LoggerFactory.getLogger(AuthenticationSuccessEventListener.class);
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {

    }
}
