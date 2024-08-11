package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;

public class UserNotFoundException extends AbstractDomainException {

    public UserNotFoundException(OAuth2User oAuth2User) {
        super(UserErrorCode.NOT_FOUND,
                "OAuth2Provider=" + oAuth2User.getProvider() + ", OAuth2Id=" + oAuth2User.getId()
        );
    }
}
