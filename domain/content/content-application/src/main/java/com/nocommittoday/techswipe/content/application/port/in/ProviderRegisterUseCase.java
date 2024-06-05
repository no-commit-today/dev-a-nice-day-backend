package com.nocommittoday.techswipe.content.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

public interface ProviderRegisterUseCase {

    TechContentProvider.TechContentProviderId register(final ProviderRegisterCommand command);
}
