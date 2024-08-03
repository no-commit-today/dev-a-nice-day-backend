package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import com.nocommittoday.techswipe.domain.core.PageParam;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TechContentCategorizedListReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentCategorizedListReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public List<TechContent> getList(
            PageParam pageParam,
            List<TechCategory> categories
    ) {
        return techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
                        pageParam,
                        categories
                ).stream()
                .map(TechContentEntity::toDomain)
                .toList();
    }

    public long count(List<TechCategory> categories) {
        return techContentJpaRepository.countByCategoryInAndDeletedIsFalse(categories);
    }
}
