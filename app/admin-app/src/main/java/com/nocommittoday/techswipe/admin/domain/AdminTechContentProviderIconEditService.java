package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.exception.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminImageEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminTechContentProviderEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class AdminTechContentProviderIconEditService {

    private final AdminImageEntityJpaRepository adminImageEntityJpaRepository;
    private final AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository;

    public AdminTechContentProviderIconEditService(
            AdminImageEntityJpaRepository adminImageEntityJpaRepository,
            AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository
    ) {
        this.adminImageEntityJpaRepository = adminImageEntityJpaRepository;
        this.adminTechContentProviderEntityJpaRepository = adminTechContentProviderEntityJpaRepository;
    }

    @Transactional
    public void edit(TechContentProviderId id, @Nullable ImageId iconId) {
        TechContentProviderEntity providerEntity = adminTechContentProviderEntityJpaRepository.findById(id.value())
                .filter(TechContentProviderEntity::isUsed)
                .orElseThrow(() -> new TechContentProviderNotFoundException(id));

        if (providerEntity.getIcon() != null) {
            providerEntity.getIcon().delete();
        }

        ImageEntity imageEntity = Optional.ofNullable(iconId)
                .map(ImageId::value)
                .flatMap(adminImageEntityJpaRepository::findById)
                .filter(ImageEntity::isUsed)
                .orElse(null);

        adminTechContentProviderEntityJpaRepository.updateIconById(id.value(), imageEntity);
    }
}
