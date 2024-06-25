package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;

import java.util.List;
import java.util.Optional;

interface TechContentJpaRepositoryCustom {

    List<TechContentEntity> findAllWithProviderByCategoryInOrderByPublishedDateDesc(
            PageParam pageParam, List<TechCategory> categories
    );

    long countByDeletedIsFalseCategoryIn(List<TechCategory> categories);

    List<TechContentEntity> findAllWithProviderOrderByPublishedDateDesc(PageParam pageParam);

    Optional<String> findUrlByIdAndDeletedIsFalse(Long id);
}
