package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.admin.domain.exception.AdminTechContentProviderAlreadyExistsException;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminImageEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminTechContentProviderEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class AdminTechContentProviderRegisterService {

    private final AdminImageEntityJpaRepository adminImageEntityJpaRepository;
    private final AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository;
    private final AdminTechContentProviderIdGenerator techContentProviderIdGenerator;

    public AdminTechContentProviderRegisterService(
            AdminImageEntityJpaRepository adminImageEntityJpaRepository,
            AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository,
            AdminTechContentProviderIdGenerator techContentProviderIdGenerator
    ) {
        this.adminImageEntityJpaRepository = adminImageEntityJpaRepository;
        this.adminTechContentProviderEntityJpaRepository = adminTechContentProviderEntityJpaRepository;
        this.techContentProviderIdGenerator = techContentProviderIdGenerator;
    }

    public TechContentProviderId register(AdminTechContentProviderRegisterCommand command) {
        if (adminTechContentProviderEntityJpaRepository.existsByUrl(command.url())) {
            throw new AdminTechContentProviderAlreadyExistsException(URI.create(command.url()));
        }

        ImageEntity imageEntity = Optional.ofNullable(command.iconId())
                .map(ImageId::value)
                .flatMap(adminImageEntityJpaRepository::findById)
                .filter(ImageEntity::isUsed)
                .orElse(null);

        TechContentProviderId providerId = techContentProviderIdGenerator.nextId();
        adminTechContentProviderEntityJpaRepository.save(new TechContentProviderEntity(
                providerId.value(),
                command.type(),
                command.title(),
                command.url(),
                imageEntity
        ));

        return providerId;
    }
}
