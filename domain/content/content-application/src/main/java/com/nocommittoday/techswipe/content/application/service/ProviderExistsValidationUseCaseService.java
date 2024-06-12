package com.nocommittoday.techswipe.content.application.service;

import com.nocommittoday.techswipe.content.application.port.in.ProviderExistsValidationUseCase;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.ContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.infrastructure.ProviderExistsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ProviderExistsValidationUseCaseService implements ProviderExistsValidationUseCase {

    private final ProviderExistsReader providerExistsReader;

    @Override
    public void validate(final TechContentProvider.TechContentProviderId id) {
        if (!providerExistsReader.exists(id)) {
            throw new ContentProviderNotFoundException(id);
        }
    }
}
