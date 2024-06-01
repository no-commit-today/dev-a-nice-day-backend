package com.nocommittoday.techswipe.content.application.service;

import com.nocommittoday.techswipe.content.application.port.in.ProviderExistsValidationUseCase;
import com.nocommittoday.techswipe.content.application.port.out.ProviderExistsReaderPort;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.ContentProviderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ProviderExistsValidationUseCaseService implements ProviderExistsValidationUseCase {

    private final ProviderExistsReaderPort providerExistsReaderPort;

    @Override
    public void validate(final TechContentProvider.TechContentProviderId id) {
        if (!providerExistsReaderPort.exists(id)) {
            throw new ContentProviderNotFoundException(id);
        }
    }
}
