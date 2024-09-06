package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TechContentCountReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentCountReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    public long count(List<TechCategory> categories) {
        return techContentJpaRepository.countByCategoryInAndDeletedIsFalse(categories);
    }
}
