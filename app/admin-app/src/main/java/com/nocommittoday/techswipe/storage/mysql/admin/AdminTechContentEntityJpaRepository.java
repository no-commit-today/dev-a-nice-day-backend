package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTechContentEntityJpaRepository
        extends JpaRepository<TechContentEntity, Long>, AdminTechContentEntityJpaRepositoryCustom {
}
