package com.xinguan.config;

import com.xinguan.interceptor.UrlInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhan
 * @date 2019-04-04 20:13
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private UrlInterceptor urlInterceptor;

    @Autowired
    public InterceptorConfig(UrlInterceptor urlInterceptor) {
        this.urlInterceptor = urlInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludUrl = new ArrayList<>();
        excludUrl.add("/webjars/**");
        excludUrl.add("/swagger-ui.html");
        excludUrl.add("/swagger-ui.html#!/**");
        excludUrl.add("/swagger-resources/**");
        excludUrl.add("/v2/**");
        excludUrl.add("/oauth/token");
        excludUrl.add("/employee/saveEmployeeTemp");
        registry.addInterceptor(urlInterceptor).addPathPatterns("/**").excludePathPatterns(excludUrl);
    }
}
