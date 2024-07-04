package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderUrlExistsException;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderAppender;
import com.nocommittoday.techswipe.content.infrastructure.ProviderUrlExistsReader;
import com.nocommittoday.techswipe.image.infrastructure.ImageIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechContentProviderRegisterService {

    private final ImageIdValidator imageIdValidator;
    private final TechContentProviderAppender providerSave;
    private final ProviderUrlExistsReader providerUrlExistsReader;

    public TechContentProvider.Id register(final TechContentProviderRegisterCommand command) {
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
