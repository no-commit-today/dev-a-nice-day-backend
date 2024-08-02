package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TechContentUpdater {

    private final TechContentJpaRepository techContentJpaRepository;
    private final TechContentEntityMapper techContentEntityMapper;

    public TechContentUpdater(
            TechContentJpaRepository techContentJpaRepository, TechContentEntityMapper techContentEntityMapper
    ) {
        this.techContentJpaRepository = techContentJpaRepository;
        this.techContentEntityMapper = techContentEntityMapper;
    }

    @Transactional
    public void update(TechContent techContent) {
        if (!techContentJpaRepository.existsById(techContent.getId().value())) {
            throw new TechContentNotFoundException(techContent.getId());
        }
        techContentJpaRepository.save(techContentEntityMapper.from(techContent));
    }
}
