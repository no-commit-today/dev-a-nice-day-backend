package com.nocommittoday.techswipe.content.application.port.out;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

public interface ProviderExistsReaderPort {

    boolean exists(final TechContentProvider.TechContentProviderId id);
}
