package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.TechContentProviderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TechContentProviderIdValidator {

    private final TechContentProviderExistsReader techContentProviderExistsReader;

    public void validate(final TechContentProvider.Id id) {
        if (!techContentProviderExistsReader.exists(id)) {
            throw new TechContentProviderNotFoundException(id);
        }
    }
}
