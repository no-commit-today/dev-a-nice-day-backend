package com.nocommittoday.techswipe.content.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

public interface ProviderExistsValidationUseCase {

    void validate(final TechContentProvider.TechContentProviderId id);
}
