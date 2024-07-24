package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderReader;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderUpdater;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.infrastructure.ImageIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class TechContentProviderIconEditService {

    private final TechContentProviderReader techContentProviderReader;

    private final TechContentProviderUpdater techContentProviderUpdater;

    private final ImageIdValidator imageIdValidator;

    public void edit(final TechContentProvider.Id providerId, @Nullable final ImageId iconId) {
        if (iconId != null) {
            imageIdValidator.validate(iconId);
        }
        final TechContentProvider provider = techContentProviderReader.get(providerId);
        final TechContentProvider editedProvider = provider.editIcon(iconId);
        techContentProviderUpdater.update(editedProvider);
    }
}
