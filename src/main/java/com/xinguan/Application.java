package com.xinguan;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhangzhan
 * @date 2019-01-02 16:24
 */

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableTransactionManagement
@EnableResourceServer
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    @Value("${http.port}")
    private Integer port;

    @Bean
    public TomcatServletWebServerFactory ServletWebServerFactoryservletContainer() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector);
        return tomcatServletWebServerFactory;
    }

}
