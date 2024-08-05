package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TechContentReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public TechContent getIncludingDeleted(TechContentId id) {
        return techContentJpaRepository.findById(id.value())
                .map(TechContentEntity::toDomain)
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public TechContent get(TechContentId id) {
        return techContentJpaRepository.findById(id.value())
                .filter(TechContentEntity::isUsed)
                .map(TechContentEntity::toDomain)
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
