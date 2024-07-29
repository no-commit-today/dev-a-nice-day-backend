package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntityMapper;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TechContentUpdater {

    private final TechContentJpaRepository techContentJpaRepository;

    private final TechContentEntityMapper techContentEntityMapper;

    @Transactional
    public void update(final TechContent techContent) {
        if (!techContentJpaRepository.existsById(techContent.getId().value())) {
            throw new TechContentNotFoundException(techContent.getId());
        }
        techContentJpaRepository.save(techContentEntityMapper.from(techContent));
    }
}
