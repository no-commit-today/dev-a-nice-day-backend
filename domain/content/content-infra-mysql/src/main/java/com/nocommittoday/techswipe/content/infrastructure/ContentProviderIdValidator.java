package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentProviderIdValidator {

    private final ProviderExistsReader providerExistsReader;

    public void validate(final TechContentProvider.Id id) {
        if (!providerExistsReader.exists(id)) {
            throw new TechContentProviderNotFoundException(id);
        }
    }
}
