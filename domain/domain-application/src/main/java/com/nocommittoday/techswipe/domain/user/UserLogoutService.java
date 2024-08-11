package com.nocommittoday.techswipe.domain.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserLogoutService {

    private static final Logger log = LoggerFactory.getLogger(UserLogoutService.class);

    private final LoggedInUserReader loggedInUserReader;
    private final LogoutProcessor logoutProcessor;

    public UserLogoutService(LoggedInUserReader loggedInUserReader, LogoutProcessor logoutProcessor) {
        this.loggedInUserReader = loggedInUserReader;
        this.logoutProcessor = logoutProcessor;
    }

    public void logout(RefreshTokenDecoded refreshTokenDecoded) {
        if (!refreshTokenDecoded.isValid()) {
            log.warn("유효하지 않은 토큰입니다.", refreshTokenDecoded.getException());
            return;
        }

        RefreshToken refreshToken = refreshTokenDecoded.verify();
        LoggedInUser loggedInUser = loggedInUserReader.get(refreshToken.getId());
        logoutProcessor.process(loggedInUser);
    }
}
