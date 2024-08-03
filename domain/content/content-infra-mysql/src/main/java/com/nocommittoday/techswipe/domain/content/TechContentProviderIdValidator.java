package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentProviderNotFoundException;
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
