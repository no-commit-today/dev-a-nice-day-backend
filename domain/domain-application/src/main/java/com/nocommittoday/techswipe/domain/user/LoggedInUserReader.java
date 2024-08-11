package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LoggedInUserReader {

    private final LoggedInEntityJpaRepository loggedInEntityJpaRepository;

    public LoggedInUserReader(LoggedInEntityJpaRepository loggedInEntityJpaRepository) {
        this.loggedInEntityJpaRepository = loggedInEntityJpaRepository;
    }

    @Transactional(readOnly = true)
    public LoggedInUser get(RefreshTokenId refreshTokenId) {
        LoggedInEntity loggedInEntity = loggedInEntityJpaRepository
                .findByRefreshTokenId(refreshTokenId.value().toString())
                .orElseThrow(() -> new UserAuthenticationFailureException(refreshTokenId));
        if (loggedInEntity.isDeleted() || loggedInEntity.getUser().isDeleted()) {
            throw new UserAuthenticationFailureException(refreshTokenId);
        }
        return loggedInEntity.toDomain();
    }
}
