package com.nocommittoday.techswipe.domain.content.provider;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.DomainValidationException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class TechContentProviderIdValidator {

    private final TechContentProviderJpaRepository repository;

    public TechContentProviderIdValidator(TechContentProviderJpaRepository repository) {
        this.repository = repository;
    }

    public void validate(TechContentProviderId id) {
        if (!repository.existsByIdAndDeletedIsFalse(id.value())) {
            throw new DomainValidationException("잘못된 컨텐츠 제공자 ID 입니다.", id);
        }
    }
}
