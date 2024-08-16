package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentCategoryEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nocommittoday.techswipe.storage.mysql.content.QTechContentCategoryEntity.techContentCategoryEntity;
import static com.nocommittoday.techswipe.storage.mysql.content.QTechContentEntity.techContentEntity;

public class AdminTechContentEntityJpaRepositoryCustomImpl implements AdminTechContentEntityJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public AdminTechContentEntityJpaRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public boolean updateCategoriesById(Long id, List<TechCategory> categories) {
        TechContentEntity entity = queryFactory.selectFrom(techContentEntity)
                .where(techContentEntity.id.eq(id))
                .fetchOne();
        if (entity == null) {
            return false;
        }

        queryFactory.delete(techContentCategoryEntity)
                .where(techContentCategoryEntity.content.eq(entity))
                .execute();

        categories.stream()
                .map(category -> new TechContentCategoryEntity(entity, category))
                .forEach(em::persist);

        return true;
    }
}
