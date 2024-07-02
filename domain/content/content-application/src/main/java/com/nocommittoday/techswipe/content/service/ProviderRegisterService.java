package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderUrlExistsException;
import com.nocommittoday.techswipe.content.infrastructure.ProviderAppender;
import com.nocommittoday.techswipe.content.infrastructure.ProviderUrlExistsReader;
import com.nocommittoday.techswipe.image.infrastructure.ImageIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderRegisterService {

    private final ImageIdValidator imageIdValidator;
    private final ProviderAppender providerSave;
    private final ProviderUrlExistsReader providerUrlExistsReader;

    public TechContentProvider.Id register(final ProviderRegisterCommand command) {
        if (providerUrlExistsReader.exists(command.url())) {
            throw new TechContentProviderUrlExistsException(command.url());
        }
        if (command.iconId() != null) {
            imageIdValidator.validate(command.iconId());
        }

        return providerSave.save(new TechContentProviderCreate(
                command.type(),
                command.title(),
                command.url(),
                command.iconId()
        ));
    }
}
