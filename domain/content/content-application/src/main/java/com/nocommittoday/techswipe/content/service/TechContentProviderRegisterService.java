package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.collection.infrastructure.TechContentProviderIdGenerator;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.exception.TechContentProviderUrlExistsException;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderAppender;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderUrlExistsReader;
import com.nocommittoday.techswipe.image.infrastructure.ImageIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechContentProviderRegisterService {

    private final ImageIdValidator imageIdValidator;
    private final TechContentProviderAppender providerSave;
    private final TechContentProviderUrlExistsReader techContentProviderUrlExistsReader;
    private final TechContentProviderIdGenerator techContentProviderIdGenerator;

    public TechContentProvider.Id register(final TechContentProviderRegisterCommand command) {
        if (techContentProviderUrlExistsReader.exists(command.url())) {
            throw new TechContentProviderUrlExistsException(command.url());
        }
        if (command.iconId() != null) {
            imageIdValidator.validate(command.iconId());
        }

        return providerSave.save(new TechContentProviderCreate(
                techContentProviderIdGenerator.nextId(),
                command.type(),
                command.title(),
                command.url(),
                command.iconId()
        ));
    }
}
