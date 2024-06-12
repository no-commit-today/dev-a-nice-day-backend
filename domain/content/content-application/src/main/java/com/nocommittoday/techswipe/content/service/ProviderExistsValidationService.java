package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.ContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.infrastructure.ProviderExistsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderExistsValidationService {

    private final ProviderExistsReader providerExistsReader;

    public void validate(final TechContentProvider.TechContentProviderId id) {
        if (!providerExistsReader.exists(id)) {
            throw new ContentProviderNotFoundException(id);
        }
    }
}
