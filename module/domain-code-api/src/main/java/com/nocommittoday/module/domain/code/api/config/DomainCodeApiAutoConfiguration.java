package com.nocommittoday.module.domain.code.api.config;

import com.nocommittoday.module.domain.code.api.DomainCodeController;
import com.nocommittoday.module.domain.code.api.DomainCodeFactory;
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
