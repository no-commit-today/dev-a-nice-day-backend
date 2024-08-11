package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.SignupUnsupportedProviderException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Token;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2UserReader;
import org.springframework.stereotype.Service;

@Service
public class UserSignupService {

    private final OAuth2UserReader oAuth2UserReader;
    private final UserAppender userAppender;

    public UserSignupService(OAuth2UserReader oAuth2UserReader, UserAppender userAppender) {
        this.oAuth2UserReader = oAuth2UserReader;
        this.userAppender = userAppender;
    }

    public UserId signUp(OAuth2Token token) {
        if (!token.getProvider().canSignUp()) {
            throw new SignupUnsupportedProviderException(token.getProvider());
        }
        OAuth2User oAuth2User = oAuth2UserReader.read(token);
        return userAppender.append(oAuth2User);
    }
}
