package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Token;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2UserReader;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserLoginService {

    private final OAuth2UserReader oAuth2UserReader;
    private final UserReader userReader;
    private final UserLoginPostProcessor userLoginPostProcessor;

    public UserLoginService(
            OAuth2UserReader oAuth2UserReader, UserReader userReader, UserLoginPostProcessor userLoginPostProcessor
    ) {
        this.oAuth2UserReader = oAuth2UserReader;
        this.userReader = userReader;
        this.userLoginPostProcessor = userLoginPostProcessor;
    }

    public LoginResult login(
            OAuth2Token oAuth2Token,
            Function<UserId, AccessToken> accessTokenIssuer,
            Function<UserId, RefreshToken> refreshTokenIssuer
    ) {
        OAuth2User oAuth2User = oAuth2UserReader.read(oAuth2Token);
        User user = userReader.get(oAuth2User);
        RefreshToken refreshToken = refreshTokenIssuer.apply(user.getId());
        AccessToken accessToken = accessTokenIssuer.apply(user.getId());
        LoggedInUser loggedInUser = user.login(refreshToken);
        userLoginPostProcessor.process(loggedInUser);
        return new LoginResult(user.getId(), accessToken, refreshToken);
    }
}
