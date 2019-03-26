package com.xinguan.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author zhangzhan
 * @date 2019-02-16 20:36
 */
@Configuration
@EnableResourceServer
public class CustomResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private CustomAccessDecisionManager customAccessDecisionManager;

    private final static Logger LOG = LoggerFactory.getLogger(CustomResourceServerConfig.class);
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .requestMatchers(new RequestMatcher() {
//                    @Override
//                    public boolean matches(HttpServletRequest httpServletRequest) {
//                        String url = httpServletRequest.getRequestURI();
//                        LOG.info(url);
//                        return true;
//                    }
//                }).permitAll()


                .antMatchers("/webjars/**","/swagger-ui.html","/swagger-ui.html#!/**","/swagger-resources/**","/v2/**","/oauth/token","/employee/saveEmployeeTemp").permitAll()
                .anyRequest().authenticated()
                .accessDecisionManager(customAccessDecisionManager)
                .and()
                .csrf().disable();

    }
}
