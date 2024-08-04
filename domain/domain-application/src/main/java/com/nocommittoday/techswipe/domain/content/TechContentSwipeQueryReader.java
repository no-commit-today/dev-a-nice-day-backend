package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaQueryRepository;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
public class TechContentSwipeQueryReader {

    private final TechContentJpaQueryRepository techContentJpaRepository;

    public TechContentSwipeQueryReader(TechContentJpaQueryRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public List<TechContentSwipeQuery> getList(
            PageParam pageParam,
            List<TechCategory> categories
    ) {
        return techContentJpaRepository.findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
                        pageParam,
                        categories
                ).stream()
                .map(entity -> new TechContentSwipeQuery(
                                new TechContentId(Objects.requireNonNull(entity.getId())),
                                entity.getUrl(),
                                entity.getTitle(),
                                entity.getPublishedDate(),
                                ImageEntity.url(entity.getImage()),
                                entity.getSummary(),
                                entity.getCategories(),
                                new TechContentProviderId(Objects.requireNonNull(entity.getProvider().getId())),
                                entity.getProvider().getType(),
                                entity.getProvider().getTitle(),
                                entity.getProvider().getUrl(),
                                ImageEntity.url(entity.getProvider().getIcon())
                        )
                ).toList();
    }
}
