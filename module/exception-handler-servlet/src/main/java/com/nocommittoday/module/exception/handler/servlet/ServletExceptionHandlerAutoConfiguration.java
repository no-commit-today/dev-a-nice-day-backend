package com.nocommittoday.module.exception.handler.servlet;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@AutoConfiguration
public class ServletExceptionHandlerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResponseEntityExceptionHandler.class)
    @ConditionalOnClass(ResponseEntityExceptionHandler.class)
    public ServletExceptionHandler globalExceptionHandler() {
        return new ServletExceptionHandler();
    }
}
