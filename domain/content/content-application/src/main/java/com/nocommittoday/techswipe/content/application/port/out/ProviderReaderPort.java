package com.nocommittoday.techswipe.content.application.port.out;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

public interface ProviderReaderPort {
    TechContentProvider get(final TechContentProvider.TechContentProviderId id);
}
