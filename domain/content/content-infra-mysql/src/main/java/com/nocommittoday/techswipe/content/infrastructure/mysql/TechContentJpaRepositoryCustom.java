package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

interface TechContentJpaRepositoryCustom {

    List<TechContentEntity> findAllWithProviderByCategoryInOrderByPublishedDateDesc(
            final Pageable pageable, final List<TechCategory> categories
    );

    List<TechContentEntity> findAllWithProviderOrderByPublishedDateDesc(final Pageable pageable);
}
