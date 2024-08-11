package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LogoutProcessor {

    private final LoggedInEntityJpaRepository loggedInEntityJpaRepository;

    public LogoutProcessor(LoggedInEntityJpaRepository loggedInEntityJpaRepository) {
        this.loggedInEntityJpaRepository = loggedInEntityJpaRepository;
    }

    @Transactional
    public void process(LoggedInUser user) {
        loggedInEntityJpaRepository.findByRefreshTokenId(
                user.getLoggedIn().getRefreshTokenId().value().toString()
        ).ifPresent(LoggedInEntity::delete);
    }
}
