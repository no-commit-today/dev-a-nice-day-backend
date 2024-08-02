package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderReader;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderUpdater;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.infrastructure.ImageIdValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class TechContentProviderIconEditService {

    private final TechContentProviderReader techContentProviderReader;
    private final TechContentProviderUpdater techContentProviderUpdater;
    private final ImageIdValidator imageIdValidator;

    public TechContentProviderIconEditService(
            TechContentProviderReader techContentProviderReader,
            TechContentProviderUpdater techContentProviderUpdater,
            ImageIdValidator imageIdValidator
    ) {
        this.techContentProviderReader = techContentProviderReader;
        this.techContentProviderUpdater = techContentProviderUpdater;
        this.imageIdValidator = imageIdValidator;
    }

    public void edit(TechContentProviderId providerId, @Nullable ImageId iconId) {
        if (iconId != null) {
            imageIdValidator.validate(iconId);
        }
        TechContentProvider provider = techContentProviderReader.get(providerId);
        TechContentProvider editedProvider = provider.editIcon(iconId);
        techContentProviderUpdater.update(editedProvider);
    }
}
