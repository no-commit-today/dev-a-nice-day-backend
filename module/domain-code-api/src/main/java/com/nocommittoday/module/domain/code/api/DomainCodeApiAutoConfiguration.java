package com.nocommittoday.module.domain.code.api;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DomainCodeApiAutoConfiguration {

    @Bean
    public DomainCodeController domainCodeController(final EnumMapperFactory enumMapperFactory) {
        return new DomainCodeController(enumMapperFactory);
    }
}
