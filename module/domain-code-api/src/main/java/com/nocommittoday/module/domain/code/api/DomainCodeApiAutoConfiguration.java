package com.nocommittoday.module.domain.code.api;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(DelegatingDomainCodeConfiguration.class)
public class DomainCodeApiAutoConfiguration {

    @Bean
    DomainCodeController domainCodeController(final DomainCodeFactory domainCodeFactory) {
        return new DomainCodeController(domainCodeFactory);
    }
}
