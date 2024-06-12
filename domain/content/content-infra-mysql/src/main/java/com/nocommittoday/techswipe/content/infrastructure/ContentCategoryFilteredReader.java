package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentCategoryFilteredReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public List<TechContent> getList(
            final PageParam pageParam,
            final List<TechCategory> categories
    ) {
        return techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
                        PageRequest.of(pageParam.page(), pageParam.size()),
                        categories
                ).stream()
                .map(TechContentEntity::toDomain)
                .toList();
    }
}