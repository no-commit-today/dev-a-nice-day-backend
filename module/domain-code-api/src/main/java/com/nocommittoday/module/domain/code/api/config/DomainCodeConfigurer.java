package com.nocommittoday.module.domain.code.api.config;

import com.nocommittoday.module.domain.code.DomainCode;

import java.util.List;

public interface DomainCodeConfigurer {

    void addDomainCodes(final List<Class<? extends DomainCode>> domainCodeTypes);
}
