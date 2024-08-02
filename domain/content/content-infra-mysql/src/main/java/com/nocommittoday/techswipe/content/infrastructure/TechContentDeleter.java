package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TechContentDeleter {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentDeleter(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional
    public void delete(TechContentId id) {
        techContentJpaRepository.findById(id.value())
                .ifPresent(TechContentEntity::delete);
    }
}
