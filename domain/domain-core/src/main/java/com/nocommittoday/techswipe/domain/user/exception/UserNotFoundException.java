package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;

import java.util.Map;

public class UserNotFoundException extends AbstractDomainException {

    public UserNotFoundException(OAuth2User oAuth2User) {
        super(UserErrorCode.NOT_FOUND,
                Map.of("provider", oAuth2User.getProvider(), "id", oAuth2User.getId())
        );
    }
}
