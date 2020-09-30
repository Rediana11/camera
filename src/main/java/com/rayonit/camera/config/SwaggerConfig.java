package com.rayonit.camera.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_INFO_TITLE = "Camera Api Documentation";
    private static final String API_INFO_DESCRIPTION = "This is the documentation page of Camera endpoints";
    private static final String API_VERSION = "1.0";
    private static final String HEADER = "header";

    @Bean
    public Docket api() {
        //@formatter:off
        List<SecurityContext> securityContexts = Arrays.asList(
                SecurityContext.builder().securityReferences(Arrays.asList((getSwaggerSecurityReference()))).build());


        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.rayonit.camera"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(getApiKey())
                .securityContexts(securityContexts)
                .apiInfo(getApiInfo());
        //@formatter:on
    }

    private SecurityReference getSwaggerSecurityReference() {
        //@formatter:off
        return SecurityReference.builder()
                .reference(HttpHeaders.AUTHORIZATION)
                .scopes(new AuthorizationScope[] {
                        new AuthorizationScopeBuilder().scope("global").description("Full access").build() }).build();
        //@formatter:on
    }

    private List<SecurityScheme> getApiKey() {
        return Collections.singletonList(new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, HEADER));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title(API_INFO_TITLE).description(API_INFO_DESCRIPTION).version(API_VERSION)
                .build();
    }

}