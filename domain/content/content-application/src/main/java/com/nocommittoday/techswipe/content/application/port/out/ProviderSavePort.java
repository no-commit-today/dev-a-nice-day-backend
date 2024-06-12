package com.nocommittoday.techswipe.content.application.port.out;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.vo.ProviderSave;

public interface ProviderSavePort {

    TechContentProvider.TechContentProviderId save(final ProviderSave command);
}
