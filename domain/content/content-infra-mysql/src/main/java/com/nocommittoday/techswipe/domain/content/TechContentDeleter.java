package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
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
