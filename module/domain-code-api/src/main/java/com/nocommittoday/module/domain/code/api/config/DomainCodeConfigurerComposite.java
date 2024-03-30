package com.nocommittoday.module.domain.code.api.config;

import com.nocommittoday.module.domain.code.DomainCode;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

class DomainCodeConfigurerComposite implements DomainCodeConfigurer {

    private final List<DomainCodeConfigurer> delegates = new ArrayList<>();

    public void addDomainCodeConfigurer(final List<DomainCodeConfigurer> configurers) {
        if (CollectionUtils.isEmpty(configurers)) {
            return;
        }
        delegates.addAll(configurers);
    }

    @Override
    public void addDomainCodes(final List<Class<? extends DomainCode>> domainCodeTypes) {
        delegates.forEach(delegate -> delegate.addDomainCodes(domainCodeTypes));
    }
}
