package com.nocommittoday.techswipe.content.application.port.out;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

public interface ProviderSavePort {

    TechContentProvider.TechContentProviderId save(final ProviderSave command);
}
