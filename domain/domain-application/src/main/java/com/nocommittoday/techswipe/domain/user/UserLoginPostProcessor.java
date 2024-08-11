package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntity;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntityJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserLoginPostProcessor {

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final LoggedInEntityJpaRepository loggedInEntityJpaRepository;

    public UserLoginPostProcessor(
            UserEntityJpaRepository userEntityJpaRepository,
            LoggedInEntityJpaRepository loggedInEntityJpaRepository
    ) {
        this.userEntityJpaRepository = userEntityJpaRepository;
        this.loggedInEntityJpaRepository = loggedInEntityJpaRepository;
    }

    @Transactional
    public void process(LoggedInUser loggedInUser) {
        UserEntity userEntity = userEntityJpaRepository.findById(loggedInUser.getId().value())
                .filter(UserEntity::isUsed)
                .orElseThrow(() -> new UserAuthenticationFailureException(loggedInUser.getId()));
        loggedInEntityJpaRepository.save(new LoggedInEntity(
                userEntity,
                loggedInUser.getLoggedIn().getRefreshTokenId().value().toString(),
                loggedInUser.getLoggedIn().getExpiresAt()
        ));
    }
}
