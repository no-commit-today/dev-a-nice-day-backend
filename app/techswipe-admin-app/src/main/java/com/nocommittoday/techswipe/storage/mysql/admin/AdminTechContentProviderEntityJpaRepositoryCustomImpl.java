package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import static com.nocommittoday.techswipe.storage.mysql.content.QTechContentProviderEntity.techContentProviderEntity;

class AdminTechContentProviderEntityJpaRepositoryCustomImpl implements AdminTechContentProviderEntityJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AdminTechContentProviderEntityJpaRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional
    public long updateIconById(Long id, ImageEntity icon) {
        return queryFactory.update(techContentProviderEntity)
                .set(techContentProviderEntity.icon, icon)
                .where(techContentProviderEntity.id.eq(id))
                .execute();
    }
}
