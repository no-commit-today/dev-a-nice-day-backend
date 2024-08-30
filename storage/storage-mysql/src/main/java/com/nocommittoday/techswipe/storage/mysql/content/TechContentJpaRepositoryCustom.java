package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.PageParam;

import java.util.List;
import java.util.Optional;

interface TechContentJpaRepositoryCustom {

    List<TechContentEntity> findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
            PageParam pageParam, List<TechCategory> categories
    );

    long countByCategoryInAndDeletedIsFalse(List<TechCategory> categories);

    List<TechContentEntity> findAllWithProviderOrderByPublishedDateDesc(PageParam pageParam);

    Optional<String> findUrlByIdAndDeletedIsFalse(Long id);
}
