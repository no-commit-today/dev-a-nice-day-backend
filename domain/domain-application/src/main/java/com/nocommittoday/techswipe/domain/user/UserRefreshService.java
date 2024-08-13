package com.nocommittoday.techswipe.domain.user;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserRefreshService {

    private final LoggedInUserReader loggedInUserReader;
    private final UserRefreshPostProcessor userRefreshPostProcessor;

    public UserRefreshService(
            LoggedInUserReader loggedInUserReader, UserRefreshPostProcessor userRefreshPostProcessor
    ) {
        this.loggedInUserReader = loggedInUserReader;
        this.userRefreshPostProcessor = userRefreshPostProcessor;
    }

    public LoginResult refresh(
            RefreshTokenDecoded refreshTokenDecoded,
            Function<UserId, AccessToken> accessTokenIssuer,
            Function<UserId, RefreshToken> refreshTokenIssuer
    ) {
        RefreshToken refreshToken = refreshTokenDecoded.verify();
        LoggedInUser loggedInUser = loggedInUserReader.get(refreshToken.getId());

        RefreshToken newRefreshToken = refreshTokenIssuer.apply(loggedInUser.getId());
        AccessToken newAccessToken = accessTokenIssuer.apply(loggedInUser.getId());
        userRefreshPostProcessor.process(loggedInUser, newRefreshToken);

        return new LoginResult(loggedInUser.getId(), newAccessToken, newRefreshToken);
    }
}
