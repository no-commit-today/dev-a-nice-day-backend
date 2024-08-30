package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TechContentListQueryReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentListQueryReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public List<TechContentQuery> getListV1(PageParam pageParam, List<TechCategory> categories) {
        return techContentJpaRepository.findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
                        pageParam, categories
                ).stream()
                .map(TechContentEntity::toQuery)
                .toList();
    }
}
