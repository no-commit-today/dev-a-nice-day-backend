package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.ProviderSave;
import com.nocommittoday.techswipe.content.infrastructure.ProviderAppender;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderRegisterService {

    private final ImageStoreService imageStoreService;
    private final ProviderAppender providerSave;

    public TechContentProvider.TechContentProviderId register(final ProviderRegisterCommand command) {
        final Image.ImageId iconId = Optional.ofNullable(command.iconUrl())
                .map(url -> imageStoreService.store(url, "provider-icon"))
                .orElse(null);
        return providerSave.save(new ProviderSave(
                command.type(),
                command.title(),
                command.url(),
                iconId
        ));
    }
}
