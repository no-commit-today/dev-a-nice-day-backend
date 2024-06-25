package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentCategorizedListReader {

    private final TechContentJpaRepository techContentJpaRepository;

    @Transactional(readOnly = true)
    public List<TechContent> getList(
            final PageParam pageParam,
            final List<TechCategory> categories
    ) {
        return techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
                        pageParam,
                        categories
                ).stream()
                .map(TechContentEntity::toDomain)
                .toList();
    }

    public long count(final List<TechCategory> categories) {
        return 0;
    }
}
