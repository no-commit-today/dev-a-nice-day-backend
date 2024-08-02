package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.domain.exception.TechContentProviderNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class TechContentProviderIdValidator {

    private final TechContentProviderExistsReader techContentProviderExistsReader;

    public TechContentProviderIdValidator(TechContentProviderExistsReader techContentProviderExistsReader) {
        this.techContentProviderExistsReader = techContentProviderExistsReader;
    }

    public void validate(TechContentProviderId id) {
        if (!techContentProviderExistsReader.exists(id)) {
            throw new TechContentProviderNotFoundException(id);
        }
    }
}
