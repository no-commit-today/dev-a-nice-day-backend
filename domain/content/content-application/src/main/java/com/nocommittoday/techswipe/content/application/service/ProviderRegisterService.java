package com.nocommittoday.techswipe.content.application.service;

import com.nocommittoday.techswipe.content.application.port.in.ProviderRegisterCommand;
import com.nocommittoday.techswipe.content.application.port.in.ProviderRegisterUseCase;
import com.nocommittoday.techswipe.content.application.port.out.ProviderSave;
import com.nocommittoday.techswipe.content.application.port.out.ProviderSavePort;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.image.application.port.in.ImageStoreUseCase;
import com.nocommittoday.techswipe.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ProviderRegisterService implements ProviderRegisterUseCase {

    private final ImageStoreUseCase imageStoreUseCase;
    private final ProviderSavePort providerSavePort;

    @Override
    public TechContentProvider.TechContentProviderId register(final ProviderRegisterCommand command) {
        final Image.ImageId iconId = Optional.ofNullable(command.iconUrl())
                .map(url -> imageStoreUseCase.store(url, "provider-icon"))
                .orElse(null);
        return providerSavePort.save(new ProviderSave(
                command.type(),
                command.title(),
                command.url(),
                iconId
        ));
    }
}
