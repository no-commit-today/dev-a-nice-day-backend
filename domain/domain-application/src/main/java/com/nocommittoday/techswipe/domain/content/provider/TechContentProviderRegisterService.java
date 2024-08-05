package com.nocommittoday.techswipe.domain.content.provider;

import com.nocommittoday.techswipe.domain.content.TechContentProviderCreate;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.exception.TechContentProviderUrlExistsException;
import com.nocommittoday.techswipe.domain.image.ImageIdValidator;
import org.springframework.stereotype.Service;

@Service
public class TechContentProviderRegisterService {

    private final ImageIdValidator imageIdValidator;
    private final TechContentProviderAppender providerSave;
    private final TechContentProviderUrlExistsReader techContentProviderUrlExistsReader;
    private final TechContentProviderIdGenerator techContentProviderIdGenerator;

    public TechContentProviderRegisterService(
            ImageIdValidator imageIdValidator,
            TechContentProviderAppender providerSave,
            TechContentProviderUrlExistsReader techContentProviderUrlExistsReader,
            TechContentProviderIdGenerator techContentProviderIdGenerator
    ) {
        this.imageIdValidator = imageIdValidator;
        this.providerSave = providerSave;
        this.techContentProviderUrlExistsReader = techContentProviderUrlExistsReader;
        this.techContentProviderIdGenerator = techContentProviderIdGenerator;
    }

    public TechContentProviderId register(TechContentProviderRegisterCommand command) {
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
