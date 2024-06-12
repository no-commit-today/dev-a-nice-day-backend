package com.nocommittoday.techswipe.content.application.service;

import com.nocommittoday.techswipe.content.application.port.in.ProviderRegisterCommand;
import com.nocommittoday.techswipe.content.application.port.in.ProviderRegisterUseCase;
import com.nocommittoday.techswipe.content.domain.vo.ProviderSave;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.infrastructure.ProviderAppender;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ProviderRegisterService implements ProviderRegisterUseCase {

    private final ImageStoreService imageStoreService;
    private final ProviderAppender providerSave;

    @Override
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
