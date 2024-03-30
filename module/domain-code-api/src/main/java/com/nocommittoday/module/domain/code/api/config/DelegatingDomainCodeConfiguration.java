package com.nocommittoday.module.domain.code.api.config;

import com.nocommittoday.module.domain.code.DomainCode;
import com.nocommittoday.module.domain.code.api.DomainCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
class DelegatingDomainCodeConfiguration {

    private final DomainCodeConfigurerComposite configurers = new DomainCodeConfigurerComposite();

    @Nullable
    private List<Class<? extends DomainCode>> domainCodes;

    @Autowired(required = false)
    void setConfigurers(final List<DomainCodeConfigurer> configurers) {
        if (CollectionUtils.isEmpty(configurers)) {
            return;
        }
        this.configurers.addDomainCodeConfigurer(configurers);
    }

    void addDomainCodes(final List<Class<? extends DomainCode>> domainCodeTypes) {
        configurers.addDomainCodes(domainCodeTypes);
    }

    List<Class<? extends DomainCode>> getDomainCodes() {
        if (domainCodes == null) {
            domainCodes = new ArrayList<>();
            addDomainCodes(domainCodes);
        }
        return domainCodes;
    }

    @Bean
    DomainCodeFactory domainCodeFactory(final DelegatingDomainCodeConfiguration configuration) {
        final DomainCodeFactory factory = new DomainCodeFactory();
        configuration.getDomainCodes().forEach(factory::put);
        return factory;
    }
}
