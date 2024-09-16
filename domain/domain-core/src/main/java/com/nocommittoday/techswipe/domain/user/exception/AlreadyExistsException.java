package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;

import java.util.Map;

public class AlreadyExistsException extends AbstractDomainException {

    public AlreadyExistsException(OAuth2User oAuth2User) {
        super(
                UserErrorCode.ALREADY_EXISTS,
                Map.of("id", oAuth2User.getId(), "provider", oAuth2User.getProvider())
        );
    }
}
