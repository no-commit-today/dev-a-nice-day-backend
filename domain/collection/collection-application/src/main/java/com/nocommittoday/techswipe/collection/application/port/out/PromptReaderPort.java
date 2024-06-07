package com.nocommittoday.techswipe.collection.application.port.out;

import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;

public interface PromptReaderPort {

    Prompt get(PromptType type, TechContentProviderType providerType);
}
