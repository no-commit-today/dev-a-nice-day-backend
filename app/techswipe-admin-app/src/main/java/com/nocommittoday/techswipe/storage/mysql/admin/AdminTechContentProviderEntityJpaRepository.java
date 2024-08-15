package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTechContentProviderEntityJpaRepository
        extends JpaRepository<TechContentProviderEntity, Long>, AdminTechContentProviderEntityJpaRepositoryCustom {
}
