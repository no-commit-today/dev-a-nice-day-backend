package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserRefreshPostProcessor {

    private final LoggedInEntityJpaRepository loggedInEntityJpaRepository;

    public UserRefreshPostProcessor(LoggedInEntityJpaRepository loggedInEntityJpaRepository) {
        this.loggedInEntityJpaRepository = loggedInEntityJpaRepository;
    }

    @Transactional
    public void process(LoggedInUser user, RefreshToken refreshToken) {
        LoggedInEntity loggedInEntity = loggedInEntityJpaRepository.findByRefreshTokenId(
                user.getLoggedIn().getRefreshTokenId().value().toString()
        ).orElseThrow(() -> new UserAuthenticationFailureException(user.getLoggedIn().getRefreshTokenId()));

        LoggedInUser userRefreshed = user.refresh(refreshToken);
        loggedInEntity.update(userRefreshed.getLoggedIn());
    }
}
