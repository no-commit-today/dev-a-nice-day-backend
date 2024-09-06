package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TechContentQueryReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentQueryReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public TechContentQuery get(TechContentId id) {
        return techContentJpaRepository.findById(id.value())
                .filter(TechContentEntity::isUsed)
                .map(TechContentEntity::toQuery)
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
