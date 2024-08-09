package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2AccessToken;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
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

    public UserId signUp(String accessToken) {
        OAuth2User oAuth2User = oAuth2UserReader.read(new OAuth2AccessToken(OAuth2Provider.GITHUB, accessToken));
        return userAppender.append(oAuth2User);
    }
}
