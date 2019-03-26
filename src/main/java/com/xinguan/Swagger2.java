package com.xinguan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhan
 * @date 2019-02-17 09:56
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .securitySchemes(securitySchemes())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xinguan.usermanage.controller"))
                .paths(PathSelectors.any())
                .build();
              //  .globalOperationParameters(setHeaderToken());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("系统接口文档")
                .version("1.0")
                .description("API接口")
                .build();
    }



    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }
}
