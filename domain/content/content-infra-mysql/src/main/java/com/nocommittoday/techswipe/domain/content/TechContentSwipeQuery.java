package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaQueryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TechContentSwipeQuery {

    private final TechContentJpaQueryRepository techContentJpaRepository;

    public TechContentSwipeQuery(TechContentJpaQueryRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public List<TechContent> getList(
            PageParam pageParam,
            List<TechCategory> categories
    ) {
        return null;
    }
}
